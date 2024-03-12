package com.example.springsecurity.security;

import com.example.springsecurity.security.entity.CustomUserDetails;
import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.enums.Role;
import com.example.springsecurity.security.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomAuthenticationProvider authenticationProvider;

    @BeforeEach
    void setUp() {
        this.userDetailsService = mock(UserDetailsServiceImpl.class);
        this.passwordEncoder = mock(BCryptPasswordEncoder.class);
        this.authenticationProvider = new CustomAuthenticationProvider(passwordEncoder);
    }

    @Test
    void authenticateWithValidCredentials() {
        String userId = "user";
        String password = "password";

        User user = User.builder()
                .userId(userId)
                .userNm("kim")
                .userPwd(passwordEncoder.encode(password))
                .userRole(Role.USER)
                .lockYn("N")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        when(userDetailsService.loadUserByUsername(userId)).thenReturn(userDetails);

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    void authenticateWithInvalidCredentials() {
        User user = User.builder()
                .userId("user")
                .userPwd(new BCryptPasswordEncoder().encode("password"))
                .userRole(Role.USER)
                .lockYn("N")
                .build();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        // 인증 토큰 생성 및 인증 시도
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("user", "wrongPassword");

        // 예외 검증
        assertThrows(BadCredentialsException.class, () -> authenticationProvider.authenticate(authenticationToken));
    }
}