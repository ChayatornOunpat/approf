package com.approf.approf.services;

import com.approf.approf.Model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel byLogin = userService.findByLogin(username);
        if (byLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(byLogin.getUsername())
                .password(byLogin.getPassword())
                .roles(byLogin.getRole().name())
                .build();
    }
}
