package com.leon.deploytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leon.deploytracker.model.Deployment;

public interface DeploymentRepository
        extends JpaRepository<Deployment, Long> {
}