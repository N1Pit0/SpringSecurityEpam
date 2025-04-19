package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

//    @Autowired
//    public void setUserService(CommonUserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    @Operation(summary = "Login in account", description = "User logs in their profile")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully logged in profile"),
//            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
//            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
//            @ApiResponse(responseCode = "409", description = "The User resource is not there OR could not perform the action"),
//            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
//    })
//    public ResponseEntity<Void> login() throws AccessDeniedException {
//
//
//        if (isFound) {
//            return ResponseEntity.ok().build();
//        }
//        throw new NoResourceException("No User found");
//    }
}
