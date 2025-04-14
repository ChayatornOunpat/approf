package com.approf.approf.controllers;

import com.approf.approf.Model.UserModel;
import com.approf.approf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel user) {
        userService.register(user);
        return "redirect:/";
    }
}
