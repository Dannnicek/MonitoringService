package com.example.endpoint_task.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MonitoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date_of_check")
    private LocalDateTime dateOfCheck;
    @Column(name = "returned_http_status_code")
    private Integer returnedHttpStatusCode;
    @Column(name = "returned_payload", columnDefinition = "LONGTEXT")
    private String returnedPayload;
    @ManyToOne
    @JoinColumn(name = "monitored_endpoint_id")
    private MonitoredEndpoint monitoredEndpoint;

    // Constructors
    public MonitoringResult() {
        // Default constructor for JPA
    }

    public MonitoringResult(LocalDateTime dateOfCheck, Integer returnedHttpStatusCode, String returnedPayload, MonitoredEndpoint monitoredEndpoint) {
        this.dateOfCheck = dateOfCheck;
        this.returnedHttpStatusCode = returnedHttpStatusCode;
        this.returnedPayload = returnedPayload;
        this.monitoredEndpoint = monitoredEndpoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(LocalDateTime dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public Integer getReturnedHttpStatusCode() {
        return returnedHttpStatusCode;
    }

    public void setReturnedHttpStatusCode(Integer returnedHttpStatusCode) {
        this.returnedHttpStatusCode = returnedHttpStatusCode;
    }

    public String getReturnedPayload() {
        return returnedPayload;
    }

    public void setReturnedPayload(String returnedPayload) {
        this.returnedPayload = returnedPayload;
    }

    public MonitoredEndpoint getMonitoredEndpoint() {
        return monitoredEndpoint;
    }

    public void setMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint) {
        this.monitoredEndpoint = monitoredEndpoint;
    }
}
