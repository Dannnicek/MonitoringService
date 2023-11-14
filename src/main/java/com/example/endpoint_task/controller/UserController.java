package com.example.endpoint_task.controller;

import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return(userService.getUserById(id));
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return(userService.getUserByUsername(username));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return(userService.getAllUsers());
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
