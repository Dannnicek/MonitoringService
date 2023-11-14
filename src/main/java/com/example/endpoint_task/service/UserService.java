package com.example.endpoint_task.service;

import com.example.endpoint_task.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByAccessToken(UUID accessToken);
    List<User> getAllUsers();
    void createUser(User user);
    void updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
