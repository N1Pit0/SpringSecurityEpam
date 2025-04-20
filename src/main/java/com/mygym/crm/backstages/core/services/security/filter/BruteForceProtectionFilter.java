package com.mygym.crm.backstages.core.services.security.filter;

import com.mygym.crm.backstages.interfaces.services.security.LoginAttemptService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BruteForceProtectionFilter implements Filter {

    private final LoginAttemptService loginAttemptService;

    public BruteForceProtectionFilter(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Intercept requests targeting the login endpoint
        if ("/CustomerRelationShipManager/error".equals(httpRequest.getRequestURI()) && httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String username = httpRequest.getParameter("username");
            System.out.println(username);
            if (username != null && loginAttemptService.isBlocked(username)) {
                // If the user is blocked, stop further processing
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is locked due to too many failed login attempts.");
                System.out.println("I am blocking " + username);
                return; // Stop the filter chain
            }
        }

        // Pass the request down the filter chain
        chain.doFilter(request, response);
    }
}
