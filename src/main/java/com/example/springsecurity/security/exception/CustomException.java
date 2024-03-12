package com.example.springsecurity.security.exception;

import com.example.springsecurity.security.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
	ErrorCode errorCode;
}
