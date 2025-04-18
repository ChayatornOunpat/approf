package com.approf.approf.controllers;

import com.approf.approf.DTO.HoursForm;
import com.approf.approf.Model.ProfAvail;
import com.approf.approf.Model.UserModel;
import com.approf.approf.Model.UserRole;
import com.approf.approf.services.ProfAvailService;
import com.approf.approf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProfAvailService profAvailService;
    private final UserService userService;

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
        if (role.equals("ROLE_PROFESSOR")) {
            List<ProfAvail> dates = profAvailService.findByUsername();
            Map<String, List<Integer>> availability = new HashMap<>();
            dates.forEach(profAvail -> {
                availability.put(profAvail.getDate().toLocalDate().toString(), profAvail.getWorkingHours());
            });
            model.addAttribute("availability", availability);
        }
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        int daysToSubtract = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        LocalDate startDate = firstDayOfMonth.minusDays(daysToSubtract);
        List<LocalDate> calendarDates = IntStream.range(0, 42)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
        model.addAttribute("calendarDates", calendarDates);
        model.addAttribute("today", LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"  + LocalDate.now().getDayOfMonth());
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
        List<UserModel> professors = userService.findAllByRole(UserRole.PROFESSOR);
        Map<String, Map<String, List<Integer>>> profAvailability = new HashMap<>();
        professors.forEach(professor -> {
            List<ProfAvail> dates = profAvailService.findByUsername(professor.getUsername());
            Map<String, List<Integer>> availability = new HashMap<>();
            dates.forEach(profAvail -> {
                availability.put(profAvail.getDate().toLocalDate().toString(), profAvail.getWorkingHours());
            });
            profAvailability.put(professor.getUsername(), availability);
        });
        model.addAttribute("availability", profAvailability);
        model.addAttribute("professors", professors);
        if (role.equals("ROLE_STUDENT")) {
            return "book";
        }
        else {
            return "redirect:/dashboard";
        }
    }
}
