package com.leon.deploytracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.leon.deploytracker.dto.CreateDeploymentRequest;
import com.leon.deploytracker.model.Deployment;
import com.leon.deploytracker.repository.DeploymentRepository;

@Service
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    public Deployment createDeployment(CreateDeploymentRequest request) {
        Deployment deployment = new Deployment(
                request.applicationName(),
                request.environment(),
                request.version(),
                request.status(),
                LocalDateTime.now()
        );


        return deploymentRepository.save(deployment);
    }

    public List<Deployment> getAllDeployments() {
        return deploymentRepository.findAll();
    }

    public Optional<Deployment> getDeploymentById(Long id) {
        return deploymentRepository.findById(id);
    }
}
