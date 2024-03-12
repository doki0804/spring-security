package com.example.springsecurity.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
//		// 인증되지 않은 경우 페이지 이동 시 사용
//		response.sendRedirect("error/error403.html");
		// 인증되지 않은 경우 에러코드 반환 시 사용
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
