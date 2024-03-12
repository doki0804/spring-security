package com.example.springsecurity.security.service.impl;

import com.example.springsecurity.security.dto.AuthDto;
import com.example.springsecurity.security.dto.AuthMapper;
import com.example.springsecurity.security.entity.Auth;
import com.example.springsecurity.security.entity.CustomUserDetails;
import com.example.springsecurity.security.repository.AuthRepository;
import com.example.springsecurity.security.service.AuthService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository ar){
        this.authRepository = ar;
    }

    @Override
    public List<AuthDto.MenuDto> getMenuByUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth==null) throw new AuthenticationCredentialsNotFoundException("인증정보 없음");
        CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
        List<AuthDto.MenuDto> menuList = new ArrayList<>();
        for(AuthDto authDto : user.getAuthList()){
            menuList.add(authDto.getMenu());
        }
        return menuList;
    }

    @Override
    public List<AuthDto> loadAuthJoinMember(String authCd) {
        List<Auth> authList = authRepository.findAllByIdAuthCdWithMenu(authCd);
        return AuthMapper.INSTANCE.toAuthList(authList);
    }
}