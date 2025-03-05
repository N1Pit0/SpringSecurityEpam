package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
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

    @Pointcut("@annotation(com.mygym.crm.backstages.Annotations.SecutiryAnnotations.SecureMethod)")
    public void secureMethod() {}

    @Around("secureMethod()")
    public Object validateCredentials(ProceedingJoinPoint joinPoint) throws Throwable {

        SecurityDTO securityDTO = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof SecurityDTO)
                .map(arg -> (SecurityDTO) arg)
                .findFirst()
                .orElse(null);

        if (securityDTO == null || !userSecurityService.authenticate(securityDTO)) {
            throw new SecurityException("Invalid credentials provided");
        }

        return joinPoint.proceed();
    }
}
