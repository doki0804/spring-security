package com.example.springsecurity.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 아이디와 비밀번호 기반의 데이터를 Form 데이터로 전송을 받아 '인증'을 담당하는 필터입니다.
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	/**
	 * 지정된 URL로 form 전송을 하였을 경우 파라미터 정보를 가져온다.
	 *
	 * @param request  from which to extract parameters and perform the
	 *                 authentication
	 * @param response the response, which may be needed if the implementation has
	 *                 to do a redirect as part of a multi-stage authentication
	 *                 process (such as OpenID).
	 * @return Authentication {}
	 * @throws AuthenticationException {}
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.debug("CustomAuthenticationFilter attemptAuthentication");
        UsernamePasswordAuthenticationToken authRequest;
        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * Request로 받은 ID와 패스워드 기반으로 토큰을 발급한다.
	 *
	 * @param request HttpServletRequest
	 * @return UsernamePasswordAuthenticationToken
	 * @throws Exception e
	 */
	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception {
		log.debug("CustomAuthenticationFilter getAuthRequest");
		try {
			// ID와 패스워드를 기반으로 토큰 발급
			return new UsernamePasswordAuthenticationToken(request.getParameter("userId"), request.getParameter("userPw"));
		} catch (UsernameNotFoundException ae) {
			throw new UsernameNotFoundException(ae.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e.getCause());
		}

	}

}
