package com.qsportfolio.backend.request.auth;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}