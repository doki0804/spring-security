package com.example.springsecurity.security;

import com.example.springsecurity.security.entity.CustomUserDetails;
import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.service.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

	@NonNull
	private BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(noRollbackFor = BadCredentialsException.class)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("CustomAuthenticationProvider authenticate");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        CustomUserDetails user = (CustomUserDetails)userDetailsService.loadUserByUsername(userId);

        if(!user.isAccountNonLocked()){
            throw new LockedException("비밀번호를 5회이상 틀렸습니다. 비밀번호 찾기 후 로그인해주세요.");
        }

        User mergedUser = entityManager.merge(user.getUser());
        if (!this.passwordEncoder.matches(userPw, user.getPassword())) {
            mergedUser.tryCntUpdate();
            mergedUser.setLocktime(LocalDateTime.now());
            if(mergedUser.getTrycnt()==5)mergedUser.setLockYn("Y");
            throw new BadCredentialsException("비밀번호가 틀렸습니다. ("+mergedUser.getTrycnt()+"/5회)");
        }
        mergedUser.setLockYn("N");

        mergedUser.setTrycnt(0L);

        user.setAuthList(authService.loadAuthJoinMember(user.getUser().getAuth()));
        return new UsernamePasswordAuthenticationToken(user, userPw, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
