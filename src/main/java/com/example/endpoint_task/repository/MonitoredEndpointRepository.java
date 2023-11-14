package com.example.endpoint_task.repository;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {
    List<MonitoredEndpoint> findByOwner(User authenticatedUser);
    MonitoredEndpoint findByName(String name);
}
