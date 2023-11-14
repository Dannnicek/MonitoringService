package com.example.endpoint_task.configuration;

import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.UUID;

@Configuration
public class InitialDataLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User user1 = new User("Applifting", "info@applifting.cz", UUID.fromString("93f39e2f-80de-4033-99ee-249d92736a25"));
            User user2 = new User("Batman", "batman@example.com", UUID.fromString("dcb20f8a-5657-4f1b-9f7f-ce65739b359e"));
            userRepository.saveAll(Arrays.asList(user1, user2));
        }
    }
}
