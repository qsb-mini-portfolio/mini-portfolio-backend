package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.auth.LoginRequest;
import com.qsportfolio.backend.request.auth.NewPasswordRequest;
import com.qsportfolio.backend.request.auth.RegisterRequest;
import com.qsportfolio.backend.service.auth.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.qsportfolio.backend.security.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    public AuthController(AuthService authService, JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.createUser(request.getUsername(), request.getPassword(), "");
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody NewPasswordRequest request, Authentication authentication) {
        String username = authentication.getName();
        authService.changePassword(username, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully!");
    }

    @PostMapping("/checkAuth")
    public ResponseEntity<Boolean> checkAuth(@RequestParam String token) {
        return ResponseEntity.ok(jwtUtil.validateToken(token));
    }


}