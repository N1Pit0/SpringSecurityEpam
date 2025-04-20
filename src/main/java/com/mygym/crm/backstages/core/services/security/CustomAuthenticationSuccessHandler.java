package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.core.services.security.jwt.JwtService;
import com.mygym.crm.backstages.interfaces.services.security.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final LoginAttemptService loginAttemptService;
    private final JwtService jwtService;

    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService, JwtService jwtService) {
        this.loginAttemptService = loginAttemptService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        // Clear failed attempts for the user
        loginAttemptService.loginSuccessful(username);

        //JWT Generation
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", authentication.getAuthorities());

        String jwtToken = jwtService.createToken(username, 24 * 60 * 60 * 1000, claims);

        response.addHeader("Authorization", "Bearer " + jwtToken);

        super.onAuthenticationSuccess(request, response, authentication); // Standard Spring Security behavior
    }
}