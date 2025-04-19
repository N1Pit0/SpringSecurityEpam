package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.interfaces.services.security.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private LoginAttemptService loginAttemptService;

    @Autowired
    public void setLoginAttemptService(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        // Track failed login attempts only when credentials are invalid
        if (exception instanceof BadCredentialsException) {
            loginAttemptService.loginFailed(username);
            System.out.println("inside onAuthenticationFailure");

            // Check if user has exceeded maximum attempts
            if (loginAttemptService.isBlocked(username)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is locked due to too many failed attempts.");
                System.out.println("user blocked");
                return;
            }
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
