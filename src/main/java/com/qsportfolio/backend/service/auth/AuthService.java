package com.qsportfolio.backend.service.auth;

import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.repository.UserRepository;
import com.qsportfolio.backend.security.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public void createUser(String username, String password) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already taken!");
        }

        User user = new User(
            UUID.randomUUID(),
            username,
            passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public String login(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) { // Check hash
            throw new Exception("Invalid password!");
        }

        return jwtUtil.generateToken(username);
    }

}
