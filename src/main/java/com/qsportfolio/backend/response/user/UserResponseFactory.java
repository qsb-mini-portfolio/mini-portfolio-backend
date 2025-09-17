package com.qsportfolio.backend.response.user;

import com.qsportfolio.backend.domain.user.User;

public class UserResponseFactory {

    public static UserResponse createUserResponse(User user) {
        return new UserResponse(
                user.getUsername()
        );
    }
}
