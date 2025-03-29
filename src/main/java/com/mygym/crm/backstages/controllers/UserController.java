package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.core.services.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserSecurityService userSecurityService;
    private UserService userService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Void> login(@RequestBody SecurityDto securityDto) throws AccessDeniedException {
        userService.validateDto(securityDto);

        boolean isFound = userSecurityService.authenticate(securityDto, securityDto.getUserName());

        if (isFound) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
