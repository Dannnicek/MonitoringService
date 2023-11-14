package com.example.endpoint_task.repository;

import com.example.endpoint_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByAccessToken(UUID accessToken);
}
