package com.example.springsecurity.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

/**
 *
 */
@Slf4j
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("CustomLoginSuccessHandler");
//		RequestCache requestCache = new HttpSessionRequestCache();
////		SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//		String uri = "/main";
//
//		// JSON 응답 구성
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		PrintWriter out = response.getWriter();
//		out.print(JsonResultUtil.getJsonResult("true", Collections.singletonList(uri)));
//		out.flush();
		response.sendRedirect("/main");
	}

}