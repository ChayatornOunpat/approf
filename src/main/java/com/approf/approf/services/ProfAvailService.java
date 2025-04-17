package com.approf.approf.services;

import com.approf.approf.Model.ProfAvail;
import com.approf.approf.repository.ProfAvailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public ProfAvail findByUsername() {
        // Get authentication info inside the method, not at class level
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Optional<ProfAvail> profAvail = profAvailRepository.findByUsername(username);
        return profAvail.orElse(null);
    }
    
    /**
     * Find ProfAvail record for a specific username
     * @param username Username to search for
     * @return ProfAvail or null if not found
     */
    public ProfAvail findByUsername(String username) {
        Optional<ProfAvail> profAvail = profAvailRepository.findByUsername(username);
        return profAvail.orElse(null);
    }
}