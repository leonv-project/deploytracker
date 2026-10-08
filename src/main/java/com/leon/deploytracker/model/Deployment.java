package com.leon.deploytracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Deployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicationName;

    @Enumerated(EnumType.STRING)
    private Environment environment;

    private String version;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime deployedAt;


    public Deployment() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDeployedAt() {
        return deployedAt;
    }

    public void setDeployedAt(LocalDateTime deployedAt) {
        this.deployedAt = deployedAt;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}