package com.example.endpoint_task.service.impl;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.MonitoringResult;
import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.repository.MonitoredEndpointRepository;
import com.example.endpoint_task.service.MonitoredEndpointService;
import com.example.endpoint_task.service.MonitoringResultService;
import com.example.endpoint_task.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    private final MonitoredEndpointRepository monitoredEndpointRepository;
    private final UserService userService;
    private final MonitoringResultService monitoringResultService;

    public MonitoredEndpointServiceImpl(MonitoredEndpointRepository monitoredEndpointRepository, UserService userService, MonitoringResultService monitoringResultService) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
        this.userService = userService;
        this.monitoringResultService = monitoringResultService;
    }

    @Override
    public List<MonitoredEndpoint> getAllMonitoredEndpoints() {
        return monitoredEndpointRepository.findAll();
    }

    @Override
    public List<MonitoredEndpoint> getAllMonitoredEndpointsForUser(String accessToken) {
        User authenticatedUser = userService.getUserByAccessToken(UUID.fromString(removeBearerPrefix(accessToken)));
        return monitoredEndpointRepository.findByOwner(authenticatedUser);
    }

    @Override
    public MonitoredEndpoint getMonitoredEndpointByName(String name) {
        return monitoredEndpointRepository.findByName(name);
    }

    @Override
    public void createMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint) {
        monitoredEndpointRepository.save(monitoredEndpoint);
    }

    @Override
    public void updateMonitoredEndpoint(Long id, MonitoredEndpoint updatedEndpoint) {
        MonitoredEndpoint existingEndpoint = monitoredEndpointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MonitoredEndpoint not found with id: " + id));
        existingEndpoint.setName(updatedEndpoint.getName());
        existingEndpoint.setUrl(updatedEndpoint.getUrl());
        existingEndpoint.setDateOfCreation(updatedEndpoint.getDateOfCreation());
        existingEndpoint.setDateOfLastCheck(updatedEndpoint.getDateOfLastCheck());
        existingEndpoint.setMonitoredInterval(updatedEndpoint.getMonitoredInterval());
        existingEndpoint.setOwner(updatedEndpoint.getOwner());
        monitoredEndpointRepository.save(existingEndpoint);
    }

    @Override
    @Transactional
    public void deleteMonitoredEndpoint(Long id) {
        monitoringResultService.deleteMonitoringResultsForEndpoint(id);
        monitoredEndpointRepository.deleteById(id);
    }

    private String removeBearerPrefix(String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return accessToken;
    }
}
