package com.approf.approf.repository;

import com.approf.approf.Model.UserModel;
import com.approf.approf.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserModel> findAllByRole(UserRole role);
}

