package com.example.crudsecurityboot.repository;

import com.example.crudsecurityboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
