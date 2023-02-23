package com.project.login.repository;

import com.project.login.controllers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    Boolean existsByusername(String userName);

}
