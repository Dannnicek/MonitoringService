package com.example.endpoint_task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Entity
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    @Column(unique = true)
    private String name;
    @NotBlank(message = "URL is required")
    @URL(message = "Invalid URL format")
    private String url;
    @Column(name = "date_of_creation")
    private LocalDateTime dateOfCreation;
    @Column(name = "date_of_last_check")
    private LocalDateTime dateOfLastCheck;
    @Column(name = "monitored_interval")
    @NotNull(message = "Monitored Interval is required")
    @Positive(message = "Monitored Interval must be a positive integer")
    private Integer monitoredInterval;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User owner;

    public MonitoredEndpoint() {

    }

    public MonitoredEndpoint(String name, String url, Integer monitoredInterval, User owner) {
        this.name = name;
        this.url = url;
        this.dateOfCreation = LocalDateTime.now();
        this.dateOfLastCheck = LocalDateTime.now();
        this.monitoredInterval = monitoredInterval;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getDateOfLastCheck() {
        return dateOfLastCheck;
    }

    public void setDateOfLastCheck(LocalDateTime dateOfLastCheck) {
        this.dateOfLastCheck = dateOfLastCheck;
    }

    public Integer getMonitoredInterval() {
        return monitoredInterval;
    }

    public void setMonitoredInterval(Integer monitoredInterval) {
        this.monitoredInterval = monitoredInterval;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
