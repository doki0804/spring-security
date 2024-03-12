package com.example.springsecurity.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ADMIN,
	ADMIN_USER,
	USER,
	GUEST;
}
