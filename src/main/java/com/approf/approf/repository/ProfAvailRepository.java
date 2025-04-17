package com.approf.approf.repository;

import com.approf.approf.Model.ProfAvail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfAvailRepository extends JpaRepository<ProfAvail, Long> {
    
    Optional<ProfAvail> findByUsername(String username);
}