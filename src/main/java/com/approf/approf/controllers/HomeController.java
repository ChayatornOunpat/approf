package com.approf.approf.controllers;

import com.approf.approf.Model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


}
