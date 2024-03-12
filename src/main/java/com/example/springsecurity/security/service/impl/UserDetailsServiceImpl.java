package com.example.springsecurity.security.service.impl;

import com.example.springsecurity.security.entity.CustomUserDetails;
import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository ur) {
		this.userRepository = ur;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) {
		if (userId == null || userId.trim().isEmpty()) {
			throw new AuthenticationServiceException("User ID is missing.");
		}
		User userEntity = userRepository.findByUserId(userId)
				.orElseThrow(() -> new BadCredentialsException("존재하지 않는 아이디입니다."));

		return new CustomUserDetails(userEntity);
	}
}
