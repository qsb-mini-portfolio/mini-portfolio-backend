package com.qsportfolio.backend.service.auth;

import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new Exception("User not found!");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) { // Check hash
            throw new Exception("Invalid password!");
        }

        return "token";
    }

}
