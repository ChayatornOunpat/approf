package com.approf.approf.controllers;

import com.approf.approf.DTO.HoursForm;
import com.approf.approf.Model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("hoursForm", new HoursForm());
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        int daysToSubtract = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        LocalDate startDate = firstDayOfMonth.minusDays(daysToSubtract);
        List<LocalDate> calendarDates = IntStream.range(0, 42)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
        model.addAttribute("today", LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"  + LocalDate.now().getDayOfMonth());
        model.addAttribute("calendarDates", calendarDates);
        model.addAttribute("month", now.getMonth().toString().toLowerCase() + " " + now.getYear());
        if (role.equals("ROLE_STUDENT")) {
            return "dashboard";
        }
        else {
            return "professorDashboard";
        }
    }

    @RequestMapping("/book")
    public String book(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("username", username);
        model.addAttribute("role", role);
        if (role.equals("ROLE_STUDENT")) {
            return "book";
        }
        else {
            return "redirect:/dashboard";
        }
    }
}
