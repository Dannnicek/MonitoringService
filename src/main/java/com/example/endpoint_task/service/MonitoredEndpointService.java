package com.example.endpoint_task.service;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import java.util.List;

public interface MonitoredEndpointService {
    List<MonitoredEndpoint> getAllMonitoredEndpoints();
    List<MonitoredEndpoint> getAllMonitoredEndpointsForUser(String accessToken);
    MonitoredEndpoint getMonitoredEndpointByName(String name);
    void createMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint);
    void updateMonitoredEndpoint(Long id, MonitoredEndpoint updatedEndpoint);
    void deleteMonitoredEndpoint(Long id);
}
