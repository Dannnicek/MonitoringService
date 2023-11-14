package com.example.endpoint_task.service;

import com.example.endpoint_task.entity.MonitoringResult;
import com.example.endpoint_task.entity.User;

import java.util.List;

public interface MonitoringResultService {
    List<MonitoringResult> getAllMonitoringResults(String accessToken);
    List<MonitoringResult> getResultsForEndpoint(Long endpointId, String accessToken);
    void createMonitoringResult(MonitoringResult monitoringResult);
    void updateMonitoringResult(Long id, MonitoringResult updatedResult);
    void deleteMonitoringResult(Long id);
    void deleteMonitoringResultsForEndpoint(Long endpointId);
}
