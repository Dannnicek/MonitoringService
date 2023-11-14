package com.example.endpoint_task.controller;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.service.MonitoredEndpointService;
import com.example.endpoint_task.service.MonitoringService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endpoint")
public class MonitoredEndpointController {

    @Autowired
    private MonitoredEndpointService monitoredEndpointService;

    @Autowired
    MonitoringService monitoringService;

    @GetMapping()
    public List<MonitoredEndpoint> getAllMonitoredEndpointsForUser(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        return monitoredEndpointService.getAllMonitoredEndpointsForUser(accessToken);
    }

    @PostMapping
    public void createMonitoredEndpoint(@RequestBody MonitoredEndpoint monitoredEndpoint) {
        monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint);
        monitoringService.addMonitoredEndpoint(monitoredEndpoint);
    }

    @PutMapping("/{id}")
    public void updateMonitoredEndpoint(@PathVariable Long id, @RequestBody MonitoredEndpoint monitoredEndpoint) {
        monitoredEndpointService.updateMonitoredEndpoint(id, monitoredEndpoint);
        monitoringService.updateMonitoredEndpoint(monitoredEndpoint);
    }

    @DeleteMapping("/{name}")
    public void deleteMonitoredEndpoint(@PathVariable String name) {
        MonitoredEndpoint deletedEndpoint = monitoredEndpointService.getMonitoredEndpointByName(name);
        Long id = deletedEndpoint.getId();
        monitoredEndpointService.deleteMonitoredEndpoint(id);
        monitoringService.deleteMonitoredEndpoint(id);
    }

}
