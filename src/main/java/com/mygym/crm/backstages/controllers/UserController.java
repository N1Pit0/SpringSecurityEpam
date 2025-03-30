package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.core.services.security.UserSecurityService;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Login in account", description = "User logs in their profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in profile"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "409", description = "The User resource is not there OR could not perform the action"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<Void> login(@RequestBody SecurityDto securityDto) throws AccessDeniedException {
        userService.validateDto(securityDto);

        boolean isFound = userSecurityService.authenticate(securityDto, securityDto.getUserName());

        if (isFound) {
            return ResponseEntity.ok().build();
        }
        throw new NoResourceException("No User found");
    }
}
