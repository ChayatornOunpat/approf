package com.approf.approf.repository;

import com.approf.approf.Model.StudentBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentBookingRepository extends JpaRepository<StudentBook, Long> {
    
    Optional<StudentBook> findByUsername(String username);
}