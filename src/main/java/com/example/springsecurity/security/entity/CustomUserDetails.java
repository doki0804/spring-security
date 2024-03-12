package com.example.springsecurity.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.example.springsecurity.security.dto.AuthDto;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Getter;

@Getter
@ToString
public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	private final User user;

	private List<AuthDto> authList = new ArrayList<>();

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().name()));
	}

	@Override
	public String getPassword() {
		return user.getUserPwd();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		//계정만료여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getLockYn().equals("N");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//비밀번호만료 여부
		return true;
	}

	@Override
	public boolean isEnabled() {
		//사용자활성화여부
		return false;
	}

	public void setAuthList(List<AuthDto> al){
		this.authList = al;
	}

}

