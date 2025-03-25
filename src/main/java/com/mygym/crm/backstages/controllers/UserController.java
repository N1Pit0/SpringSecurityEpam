package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.core.services.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @GetMapping
    public ResponseEntity<Void> login(@RequestBody SecurityDto securityDto) {
        boolean isFound = userSecurityService.authenticate(securityDto, securityDto.getUserName());

        if (isFound) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
