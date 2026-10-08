package com.leon.deploytracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leon.deploytracker.dto.CreateDeploymentRequest;
import com.leon.deploytracker.model.Deployment;
import com.leon.deploytracker.service.DeploymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/deployments")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @PostMapping
    public ResponseEntity<Deployment> createDeployment(
            @Valid @RequestBody CreateDeploymentRequest request
    ) {
        Deployment createdDeployment = 
                deploymentService.createDeployment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDeployment);
    }

    @GetMapping
    public ResponseEntity<List<Deployment>> getAllDeployments() {
        List<Deployment> deployments =
                deploymentService.getAllDeployments();

        return ResponseEntity.ok(deployments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deployment> getDeploymentById(
            @PathVariable Long id
    ) {
        return deploymentService.getDeploymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}