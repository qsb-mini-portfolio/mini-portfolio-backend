package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.users.ChangeEmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.service.user.UserService;
import com.qsportfolio.backend.response.user.UserResponseFactory;
import com.qsportfolio.backend.response.user.UserResponse;

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

    @PutMapping("/email")
    public ResponseEntity<UserResponse> modifyUserEmail(@RequestBody ChangeEmailRequest request) {
        User newUser = userService.changeUserEmail(request.getEmail());
        return ResponseEntity.ok(UserResponseFactory.createUserResponse(newUser));
    }
}
