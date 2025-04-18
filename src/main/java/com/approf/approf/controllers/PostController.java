package com.approf.approf.controllers;

import com.approf.approf.DTO.HoursForm;
import com.approf.approf.Model.ProfAvail;
import com.approf.approf.Model.UserModel;
import com.approf.approf.services.ProfAvailService;
import com.approf.approf.services.StudentBookingService;
import com.approf.approf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final ProfAvailService profAvailService;
    private final StudentBookingService studentBookingService;

    @PostMapping("/dashboard")
    public String setHours(@ModelAttribute HoursForm hoursForm) {
        List<Integer> selectedHours = hoursForm.getWorkingHours();
        Date date = new Date(hoursForm.getDay().get(0) - 1900, hoursForm.getDay().get(1) - 1, hoursForm.getDay().get(2));
        profAvailService.editAvailability(selectedHours, date);
        return "redirect:/dashboard";
    }

    @PostMapping("/book")
    public String bookSlot(@RequestParam String username,
                           @RequestParam String availability) {
        String[] parts = availability.split(" ");
        String date = parts[0];
        int hour =Integer.parseInt(parts[1]);
        LocalDateTime dateTime = LocalDateTime.parse(date + "T" + String.format("%02d", hour) + ":00:00");
        Timestamp time = Timestamp.valueOf(dateTime);
        Date justDate = new Date(time.getTime());
        try {
            studentBookingService.createBooking(username, time);
        } catch (Exception e) {
            return "redirect:/book";
        }
        List<Integer> availabilities = profAvailService.findByUsernameAndDate(username, justDate).get().getWorkingHours();
        availabilities.remove((Integer) hour);
        profAvailService.editAvailability(availabilities, justDate, username);
        return "redirect:/book"; // or another page
    }
}
