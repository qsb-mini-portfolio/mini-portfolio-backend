package com.qsportfolio.backend.request.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
}