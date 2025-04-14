package com.approf.approf.services;

import com.approf.approf.Model.UserModel;
import com.approf.approf.Model.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private static final List<UserModel> users = new ArrayList<>();
    private final PasswordEncoder passwordEncoder;

    public boolean register(UserModel user) {
        // ❌ Check for duplicate username
        if (findByLogin(user.getUsername()) != null) {
            return false; // Username already taken
        }

        // ✅ Respect the user-defined role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.add(user);
        return true;
    }

    public UserModel findByLogin(String login) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(login))
                .findFirst()
                .orElse(null);
    }
}
