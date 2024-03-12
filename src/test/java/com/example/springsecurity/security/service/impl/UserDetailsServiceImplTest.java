package com.example.springsecurity.security.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.enums.Role;
import com.example.springsecurity.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_WhenUserExists() {
        String userId = "user1";
        User user = User.builder()
                .userId("user1")
                .userNm("kim")
                .userPwd("123456")
                .auth("USER")
                .status("Active")
                .mobile("010-0000-0000")
                .email("user@example.com")
                .userRole(Role.USER)
                .build();

        // userRepository의 findByUserId 메소드가 위에서 생성한 User 객체를 반환하도록 설정
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        assertNotNull(userDetails);
        assertEquals(userId, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ThrowsException() {
        String userId = "nonExistingUser";
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> {
            userDetailsService.loadUserByUsername(userId);
        });
    }
}