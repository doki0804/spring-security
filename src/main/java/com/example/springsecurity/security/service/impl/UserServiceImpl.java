package com.example.springsecurity.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.springsecurity.security.dto.SignupDto;
import com.example.springsecurity.security.dto.UserDto;
import com.example.springsecurity.security.entity.CustomUserDetails;
import com.example.springsecurity.security.entity.User;
import com.example.springsecurity.security.enums.ErrorCode;
import com.example.springsecurity.security.enums.Role;
import com.example.springsecurity.security.exception.CustomException;
import com.example.springsecurity.security.mapper.UserMapper;
import com.example.springsecurity.security.repository.UserRepository;
import com.example.springsecurity.security.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	public UserServiceImpl(UserRepository ur) {
		this.userRepository = ur;
	}

	@Override
	public List<User> findUserList() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public String updatePw(String userId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
	        String encodingPassword = passwordEncoder().encode(userId);
	        User userInfo = userRepository.findByUserId(userId)
	        		.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	        userInfo.setUserPwd(encodingPassword);
	        userInfo.setUpUserId(user.getUser().getUserId());
			return "true";
		} catch (Exception e) {
			throw new CustomException(ErrorCode.UPDATE_ITEM);
	    }
	}

	@Transactional
	@Override
	public String userDelete(String userId) {
		try {
			userRepository.deleteById(userId);
			return "true";
		} catch (Exception e) {
			throw new CustomException(ErrorCode.DELETE_ITEM);
	    }
	}

	@Override
	@Transactional
	public String userUpdate(ArrayList<Map<String,Object>> setUserInfo) {
		UserDto.insertRequest inUser = new UserDto.insertRequest();
		CustomUserDetails user = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			for(int i=0;i<setUserInfo.size();i++) {
				if(setUserInfo.get(i).get("modFlag").equals("UPDATE")) {
				    User userItem = userRepository.findByUserId(setUserInfo.get(i).get("userId").toString())
			        		.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
				    userItem.setUserNm(setUserInfo.get(i).get("userNm").toString());
				    userItem.setStatus(setUserInfo.get(i).get("status").toString());
					userItem.setUpUserId(user.getUser().getUserId());
				}else if(setUserInfo.get(i).get("modFlag").equals("INSERT")){
					String encodingPassword = passwordEncoder().encode(setUserInfo.get(i).get("userId").toString());
					inUser.setUserId(setUserInfo.get(i).get("userId").toString());
					inUser.setUserNm(setUserInfo.get(i).get("userNm").toString());
					inUser.setUserPwd(encodingPassword);
					inUser.setAuth("USER");
					inUser.setStatus(setUserInfo.get(i).get("status").toString());
					inUser.setMobile("010-0000-0000");
					inUser.setEmail(setUserInfo.get(i).get("userId").toString() + "@example.com");
					inUser.setUserRole(Role.USER);
					inUser.setInUserId(user.getUser().getUserId());
					User userItem = UserMapper.INSTANCE.insertRequestToEntity(inUser);
					userRepository.save(userItem);
				}else {
					throw new CustomException(ErrorCode.UPDATE_ITEM);
				}
			}
			  return "true";
	    }catch(Exception e) {
	    	throw new CustomException(ErrorCode.UPDATE_ITEM);
	    }
	}


	@Override
	public UserDto.updateResponse updateResponseByUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUserId(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		return UserMapper.INSTANCE.toUpdateResponse(user);
	}

	@Transactional
	public void createAccountInfo(SignupDto.SignupUserDto signupUser) {
		if(!isValidUserId(signupUser.getUserId())) {
			throw new CustomException(ErrorCode.EXISTING_USER_ID);
		}
		// 사용자 아이디 존재 확인0
		if (isUserExists(signupUser.getUserId())) {
			System.out.println("userExist : "+signupUser.getUserId());
			throw new CustomException(ErrorCode.EXISTING_USER_ID);
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			signupUser.setInUserId(auth.getName());
			signupUser.setUserPwd(signupUser.getMobile());
		}else{
            signupUser.setInUserId(signupUser.getUserId());
		}

		String encodingPassword = passwordEncoder().encode(signupUser.getUserPwd());
		signupUser.setUserPwd(encodingPassword);

		signupUser.setAuth("GUEST");
		signupUser.setUserRole(Role.GUEST);
		signupUser.setInUserId(signupUser.getUserId());
		User saveUser = UserMapper.INSTANCE.signupUserToEntity(signupUser);
		userRepository.save(saveUser);
	}

	@Transactional
	@Override
	public void edit(UserDto.updateRequest updateRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUserId(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		user.updateUser(updateRequest.getUserNm(), updateRequest.getMobile(), updateRequest.getEmail());
	}

	@Override
	public void withdraw(String userId) { // 회원탈퇴
//		userRepository.delete(userId);
	}

	@Override
	public boolean isUserExists(String userId) {
		return userRepository.existsByUserId(userId);
	}

	@Override
	public boolean isValidUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			return false;
		}
		return userId.matches("^[a-z0-9]{5,20}$");
	}

	@Override
	public void tryCntUpdate(String userId) {
		Optional<User> user = userRepository.findByUserId(userId);
		user.ifPresent(User::tryCntUpdate);
	}


	@Override
	public PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}
}
