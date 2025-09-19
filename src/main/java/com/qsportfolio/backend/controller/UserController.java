package com.qsportfolio.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.service.user.UserService;
import com.qsportfolio.backend.response.user.UserResponseFactory;

import com.qsportfolio.backend.response.user.UserResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> returnUserData() {
        User user = userService.getUser();
        return ResponseEntity.ok(UserResponseFactory.createUserResponse(user));
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<UserResponse> modifyUserEmail(String email){
        User newUser = userService.changeUserEmail(email);
        return ResponseEntity.ok(UserResponseFactory.createUserResponse(newUser));
    }
}
