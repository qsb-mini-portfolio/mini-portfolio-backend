package com.qsportfolio.backend.service.user;

import com.qsportfolio.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.security.SecurityUtils;
import com.qsportfolio.backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUser() {
        return SecurityUtils.getCurrentUser();
    }

    public User changeUserEmail(String email){
        User user = getUser();
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}
