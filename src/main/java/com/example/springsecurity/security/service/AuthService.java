package com.example.springsecurity.security.service;

import com.example.springsecurity.security.dto.AuthDto;

import java.util.List;

public interface AuthService {
    List<AuthDto> loadAuthJoinMember(String authCd);

    List<AuthDto.MenuDto> getMenuByUser();
}
