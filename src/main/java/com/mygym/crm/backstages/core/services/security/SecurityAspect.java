package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;


@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityAspect {

    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Pointcut("@annotation(com.mygym.crm.backstages.annotations.security.SecureMethod)")
    public void secureMethod() {}

    @Around("secureMethod()")
    public Object validateCredentials(ProceedingJoinPoint joinPoint) throws Throwable {

        SecurityDto securityDto = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof SecurityDto)
                .map(arg -> (SecurityDto) arg)
                .findFirst()
                .orElse(null);

        Long id = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof Long)
                .map(arg -> (Long) arg)
                .findFirst()
                .orElse(null);

        String userName = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof String)
                .map(arg -> (String) arg)
                .findFirst()
                .orElse(null);


        if (securityDto == null) {
            throw new SecurityException("Security details not provided");
        }

        boolean isAuthenticated = false;

        if (userName != null) {
            isAuthenticated = userSecurityService.authenticate(securityDto, userName);
        } else if (id != null) {
            isAuthenticated = userSecurityService.authenticate(securityDto, id);
        } else {
            throw new SecurityException("Neither username nor id was provided");
        }

        if (!isAuthenticated) {
            throw new SecurityException("Invalid credentials provided");
        }

        return joinPoint.proceed();
    }
}
