package com.example.springsecurity.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.springsecurity.security.dto.SignupDto;
import com.example.springsecurity.security.dto.UserDto;
import com.example.springsecurity.security.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
	
	List<User> findUserList();
	String updatePw(String userId);
	String userDelete(String userId);
	String userUpdate(ArrayList<Map<String,Object>> setUserInfo);
	UserDto.updateResponse updateResponseByUserId();
	void createAccountInfo(SignupDto.SignupUserDto signupUser);
	void edit(UserDto.updateRequest updateRequest);
	void withdraw(String userId);
	boolean isUserExists(String userId);
	boolean isValidUserId(String userId);
	void tryCntUpdate(String userId);
	PasswordEncoder passwordEncoder();
	
}
