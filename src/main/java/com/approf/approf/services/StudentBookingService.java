package com.approf.approf.services;

import com.approf.approf.Model.StudentBook;
import com.approf.approf.repository.StudentBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class StudentBookingService {

    private final StudentBookingRepository studentBookingRepository;
    
    @Autowired
    public StudentBookingService(StudentBookingRepository studentBookingRepository) {
        this.studentBookingRepository = studentBookingRepository;
    }
    
    /**
     * Find booking by username
     * @param username Username to look up, or null to use current user
     * @return The StudentBook entity or null if not found
     */
    public List<StudentBook> findByUsername(String username) {
        List<StudentBook> studentBook = studentBookingRepository.findByUsername(username);
        return studentBook;
    }
    
    /**
     * Create a new booking for the currently authenticated student
     * @param profUsername The username of the professor to book
     * @param bookingDate The date of the booking
     * @return The created StudentBook entity
     */
    public StudentBook createBooking(String profUsername, Timestamp bookingDate) {
        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String studentUsername = auth.getName();
        
        // Create new booking
        StudentBook booking = new StudentBook();
        booking.setUsername(studentUsername);
        booking.setProfUsername(profUsername);
        booking.setDate(bookingDate);
        
        // Save to database (this performs the INSERT)
        return studentBookingRepository.save(booking);
    }

    public List<StudentBook> findByProfUsername(String username) {
        return studentBookingRepository.findByProfUsername(username);
    }

    
    /**
     * Alternative method that takes a complete StudentBook object
     * This might be useful when receiving data from a form or API
     */
    public StudentBook saveBooking(StudentBook studentBook) {
        // You can add validation logic here if needed
        return studentBookingRepository.save(studentBook);
    }
}