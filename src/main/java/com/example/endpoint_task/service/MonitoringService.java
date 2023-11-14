package com.example.endpoint_task.service;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.MonitoringResult;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class MonitoringService {

    private final HttpClient httpClient;
    private final MonitoredEndpointService monitoredEndpointService;
    private final MonitoringResultService monitoringResultService;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public MonitoringService(MonitoredEndpointService monitoredEndpointService, MonitoringResultService monitoringResultService) {
        this.httpClient = HttpClient.newHttpClient();
        this.monitoredEndpointService = monitoredEndpointService;
        this.monitoringResultService = monitoringResultService;
    }

    public void startMonitoring() {
        List<MonitoredEndpoint> endpoints = monitoredEndpointService.getAllMonitoredEndpoints();
        for (MonitoredEndpoint endpoint : endpoints) {
            scheduleMonitoring(endpoint);
        }
    }

    @PostConstruct
    public void initialize() {
        startMonitoring();
    }

    public void addMonitoredEndpoint(MonitoredEndpoint endpoint) {
        scheduleMonitoring(endpoint);
    }

    public void updateMonitoredEndpoint(MonitoredEndpoint endpoint) {
        stopMonitoring(endpoint.getId());
        scheduleMonitoring(endpoint);
    }

    public void deleteMonitoredEndpoint(Long endpointId) {
        stopMonitoring(endpointId);
    }

    void scheduleMonitoring(MonitoredEndpoint endpoint) {
        ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(
                () -> monitorEndpoint(endpoint),
                0,
                endpoint.getMonitoredInterval(),
                TimeUnit.SECONDS
        );
        scheduledTasks.put(endpoint.getId(), task);
    }

    void stopMonitoring(Long endpointId) {
        ScheduledFuture<?> task = scheduledTasks.get(endpointId);
        if (task != null) {
            task.cancel(true);
            scheduledTasks.remove(endpointId);
        }
    }

    public void monitorEndpoint(MonitoredEndpoint endpoint) {
        String endpointUrl = endpoint.getUrl();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            MonitoringResult result = new MonitoringResult();
            result.setDateOfCheck(LocalDateTime.now());
            result.setReturnedHttpStatusCode(statusCode);
            result.setReturnedPayload(responseBody);
            result.setMonitoredEndpoint(endpoint);
            endpoint.setDateOfLastCheck(LocalDateTime.now());
            monitoredEndpointService.updateMonitoredEndpoint(endpoint.getId(), endpoint);
            monitoringResultService.createMonitoringResult(result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
