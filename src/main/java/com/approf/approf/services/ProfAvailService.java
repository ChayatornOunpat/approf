package com.approf.approf.services;

import com.approf.approf.Model.ProfAvail;
import com.approf.approf.repository.ProfAvailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProfAvailService {
    
    private final ProfAvailRepository profAvailRepository;
    
    @Autowired
    public ProfAvailService(ProfAvailRepository profAvailRepository) {
        this.profAvailRepository = profAvailRepository;
    }
    
    /**
     * Find ProfAvail record for the currently authenticated user
     * @return ProfAvail for current user or null if not found
     */
    public List<ProfAvail> findByUsername() {
        // Get authentication info inside the method, not at class level
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        List<ProfAvail> profAvail = profAvailRepository.findAllByUsername(username);
        return profAvail;
    }
    
    /**
     * Find ProfAvail record for a specific username
     * @param username Username to search for
     * @return ProfAvail or null if not found
     */
    public List<ProfAvail> findByUsername(String username) {
        List<ProfAvail> profAvail = profAvailRepository.findAllByUsername(username);
        return profAvail;
    }

    public Optional<ProfAvail> findByUsernameAndDate(String username, Date date) {
        return profAvailRepository.findByUsernameAndDate(username, date);
    }

    public ProfAvail editAvailability(List<Integer> time, Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return profAvailSaver(time, date, username);
    }

    private ProfAvail profAvailSaver(List<Integer> time, Date date, String username) {
        Optional<ProfAvail> optional = profAvailRepository.findByUsernameAndDate(username, date);

        ProfAvail profAvail = optional.orElseGet(ProfAvail::new);
        profAvail.setUsername(username);
        profAvail.setDate(date);
        profAvail.setWorkingHours(time);

        return profAvailRepository.save(profAvail);
    }

    public ProfAvail editAvailability(List<Integer> time, Date date, String username) {
        return profAvailSaver(time, date, username);
    }
}