package com.approf.approf.services;

import com.approf.approf.Model.UserModel;
import com.approf.approf.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean register(UserModel user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public UserModel findByLogin(String login) {
        return userRepository.findByUsername(login).orElse(null);
    }
}
