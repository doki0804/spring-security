package com.example.springsecurity.security;

import com.example.springsecurity.security.dto.AuthDto;
import com.example.springsecurity.security.entity.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class AuthorizationChecker {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean check(HttpServletRequest request, Authentication auth) {
        Object principalObj = auth.getPrincipal();
        if (!(principalObj instanceof CustomUserDetails)) {
            return false;
        }
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        System.out.println("uri : " + request.getRequestURI());
        System.out.println("servletPath : " + request.getServletPath());
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/dashboard") || requestUri.equals("/getMenuData")) {
            return true;
        }
        String action = getActionForUri(requestUri);

        for (AuthDto authDto : user.getAuthList()) {
            if (antPathMatcher.match(authDto.getMenu().getProUrl() + "/**", requestUri)) {
                return hasPermissionForAction(authDto, action);
            }
        }
        return false;
    }

    private String getActionForUri(String uri) {
        if (uri.endsWith("/select")) return "AUTH_SELECT";
        if (uri.endsWith("/insert")) return "AUTH_INSERT";
        if (uri.endsWith("/update")) return "AUTH_UPDATE";
        if (uri.endsWith("/delete")) return "AUTH_DELETE";
        if (uri.endsWith("/download")) return "AUTH_DOWNLOAD";
        if (uri.endsWith("/print")) return "AUTH_PRINT";
        return "otherUri";
    }

    private boolean hasPermissionForAction(AuthDto authDto, String action) {
        switch (action) {
            case "AUTH_SElECT":
                return "Y".equals(authDto.getAuthSelect());
            case "AUTH_INSERT":
                return "Y".equals(authDto.getAuthInsert());
            case "AUTH_UPDATE":
                return "Y".equals(authDto.getAuthUpdate());
            case "AUTH_DELETE":
                return "Y".equals(authDto.getAuthDelete());
            case "AUTH_DOWNLOAD":
                return "Y".equals(authDto.getAuthDownload());
            case "AUTH_PRINT":
                return "Y".equals(authDto.getAuthPrint());
            case "otherUri":
                return true;
        }
        return false;
    }

}
