package com.approf.approf.services;

import com.approf.approf.Model.UserModel;
import com.approf.approf.Model.UserRole;
import com.approf.approf.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserModel user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserModel findByLogin(String login) {
        return userRepository.findByUsername(login).orElse(null);
    }

    public List<UserModel> findAllByRole(UserRole role) {return  userRepository.findAllByRole(role);}
}
