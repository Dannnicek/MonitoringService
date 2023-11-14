package com.example.endpoint_task.repository;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {
    List<MonitoringResult> findByMonitoredEndpointId(Long endpointId);

    List<MonitoringResult> findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(MonitoredEndpoint monitoredEndpoint);
    void deleteByMonitoredEndpointId(Long endpointId);
}
