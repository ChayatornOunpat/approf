package com.approf.approf.repository;

import com.approf.approf.Model.ProfAvail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfAvailRepository extends JpaRepository<ProfAvail, Long> {

    List<ProfAvail> findAllByUsername(String username);

    void deleteByUsernameAndDate(String username, Date date);

    Optional<ProfAvail> findByUsernameAndDate(String username, Date date);
}