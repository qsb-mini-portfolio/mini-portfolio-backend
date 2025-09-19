package com.qsportfolio.backend.service.auth;

import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.UserRepository;
import com.qsportfolio.backend.security.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(String username, String password, String email  ) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AppException("Username already taken!");
        }

        User user = new User(
            UUID.randomUUID(),
            username,
            passwordEncoder.encode(password),
            email
            );

        userRepository.save(user);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException("Invalid password!");
        }

        return jwtUtil.generateToken(username);
    }

    public void changePassword(String username, String oldPassword, String newPassword){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("User not found!"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException("Invalid old password!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
