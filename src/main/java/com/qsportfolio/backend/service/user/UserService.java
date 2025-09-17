package com.qsportfolio.backend.service.user;

import org.springframework.stereotype.Service;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.security.SecurityUtils;


@Service
public class UserService {

    public User getUser() {
        return SecurityUtils.getCurrentUser();
    }
}
