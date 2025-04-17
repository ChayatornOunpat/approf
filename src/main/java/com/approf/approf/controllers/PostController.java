package com.approf.approf.controllers;

import com.approf.approf.DTO.HoursForm;
import com.approf.approf.Model.UserModel;
import com.approf.approf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;

    @PostMapping("/dashboard")
    public String setHours(@ModelAttribute HoursForm hoursForm) {
        List<Integer> selectedHours = hoursForm.getWorkingHours();
        System.out.println(selectedHours);
        // Process selected hours...
        return "redirect:/dashboard";
    }
}
