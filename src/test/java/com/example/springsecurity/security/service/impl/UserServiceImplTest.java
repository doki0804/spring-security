package com.example.springsecurity.security.service.impl;

import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.enums.Role;
import com.example.springsecurity.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserList() {
        User user = User.builder()
                .userId("user1")
                .userNm("User Name")
                .userPwd("password")
                .auth("USER")
                .status("Active")
                .mobile("010-0000-0000")
                .email("user@example.com")
                .userRole(Role.USER)
                .build();
        // Repository 메서드 호출 모킹
        when(userRepository.findAll()).thenReturn(List.of(user));

        // 테스트 실행
        List<User> users = userService.findUserList();

        // 검증
        assertNotNull(users);
        assertFalse(users.isEmpty());

        // Repository 호출 확인
        verify(userRepository).findAll();
    }

    @Test
    void updatePw() {
    }

    @Test
    void userDelete() {
    }

    @Test
    void userUpdate() {
    }

    @Test
    void updateResponseByUserId() {
    }

    @Test
    void createAccountInfo() {
    }

    @Test
    void edit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void isUserExists() {
    }

    @Test
    void isValidUserId() {
    }

    @Test
    void tryCntUpdate() {
    }

    @Test
    void passwordEncoder() {
    }
}