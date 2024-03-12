package com.example.springsecurity.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.springsecurity.security.utils.JsonResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * 사용자의 ‘인증’에 대해 실패하였을 경우 수행되는 Handler로 실패에 대한 사용자에게 반환값을 구성하여 전달합니다.
 */
@Configuration
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // [STEP1] 클라이언트로 전달 할 응답 값을 구성합니다.
        String failMsg = "";
        String errorCd = "000";

        // [STEP2] 발생한 Exception 에 대해서 확인합니다.
        if (exception instanceof AuthenticationServiceException) {
            failMsg = exception.getMessage();
            errorCd = "001";

        } else if (exception instanceof BadCredentialsException) {
            failMsg = exception.getMessage();
            errorCd = "002";


        } else if (exception instanceof LockedException) {
            failMsg = exception.getMessage();
            errorCd = "003";

        } else if (exception instanceof DisabledException) {
            failMsg = exception.getMessage();
            errorCd = "004";

        } else if (exception instanceof AccountExpiredException) {
            failMsg = exception.getMessage();
            errorCd = "005";

        } else if (exception instanceof CredentialsExpiredException) {
            failMsg = exception.getMessage();
            errorCd = "006";
        }
        // [STEP4] 응답 값을 구성하고 전달합니다.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String jsonResult = JsonResultUtil.getJsonResult("true",errorCd,failMsg);
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}
