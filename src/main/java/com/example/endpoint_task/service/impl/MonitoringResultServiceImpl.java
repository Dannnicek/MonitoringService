package com.example.endpoint_task.service.impl;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.MonitoringResult;
import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.repository.MonitoredEndpointRepository;
import com.example.endpoint_task.repository.MonitoringResultRepository;
import com.example.endpoint_task.service.MonitoringResultService;
import com.example.endpoint_task.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class MonitoringResultServiceImpl implements MonitoringResultService {

    @Autowired
    private MonitoringResultRepository monitoringResultRepository;

    @Autowired
    private MonitoredEndpointRepository monitoredEndpointRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<MonitoringResult> getAllMonitoringResults(String accessToken) {
        User authenticatedUser = userService.getUserByAccessToken(UUID.fromString(removeBearerPrefix(accessToken)));
        List<MonitoredEndpoint> monitoredEndpoints = monitoredEndpointRepository.findByOwner(authenticatedUser);

        List<MonitoringResult> results = new ArrayList<>();
        for (MonitoredEndpoint endpoint : monitoredEndpoints) {
            List<MonitoringResult> endpointResults = monitoringResultRepository.findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(endpoint);
            results.addAll(endpointResults);
        }

        return results;
    }


    @Override
    public List<MonitoringResult> getResultsForEndpoint(Long endpointId, String accessToken) {
        User authenticatedUser = userService.getUserByAccessToken(UUID.fromString(removeBearerPrefix(accessToken)));
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointRepository.findById(endpointId).orElse(null);

        // Check if the monitoredEndpoint belongs to the authenticated user
        if (monitoredEndpoint != null && monitoredEndpoint.getOwner().equals(authenticatedUser)) {
            return monitoringResultRepository.findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(monitoredEndpoint);
        }

        return Collections.emptyList();
    }

    private String removeBearerPrefix(String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return accessToken;
    }

    @Override
    public void createMonitoringResult(MonitoringResult monitoringResult) {
        monitoringResultRepository.save(monitoringResult);
    }

    @Override
    public void updateMonitoringResult(Long id, MonitoringResult updatedResult) {
        MonitoringResult monitoringResult = monitoringResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Monitoring result not found with id: " + id));
        monitoringResult.setDateOfCheck(updatedResult.getDateOfCheck());
        monitoringResult.setReturnedHttpStatusCode(updatedResult.getReturnedHttpStatusCode());
        monitoringResult.setReturnedPayload(updatedResult.getReturnedPayload());
        monitoringResult.setMonitoredEndpoint(updatedResult.getMonitoredEndpoint());
        monitoringResultRepository.save(monitoringResult);
    }

    @Override
    public void deleteMonitoringResult(Long id) {
        monitoringResultRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMonitoringResultsForEndpoint(Long endpointId) {
        monitoringResultRepository.deleteByMonitoredEndpointId(endpointId);
    }
}
