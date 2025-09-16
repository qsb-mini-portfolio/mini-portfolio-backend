package com.qsportfolio.backend.request.auth;

import lombok.Data;

@Data
public class NewPasswordRequest {
    String oldPassword;
    String newPassword;
}
