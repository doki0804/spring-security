package com.example.springsecurity.security;

import com.example.springsecurity.security.AuthorizationChecker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component("authorizationManager")
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AuthorizationChecker authorizationChecker;

    public CustomAuthorizationManager(AuthorizationChecker authorizationChecker) {
        this.authorizationChecker = authorizationChecker;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        Authentication auth = authentication.get();

        boolean granted = authorizationChecker.check(request, auth);
        return new AuthorizationDecision(granted);
    }
}