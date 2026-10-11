package com.leon.deploytracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leon.deploytracker.dto.CreateDeploymentRequest;
import com.leon.deploytracker.model.Deployment;
import com.leon.deploytracker.model.Environment;
import com.leon.deploytracker.model.Status;
import com.leon.deploytracker.repository.DeploymentRepository;

@ExtendWith(MockitoExtension.class)
class DeploymentServiceTest {

    @Mock
    private DeploymentRepository deploymentRepository;

    @InjectMocks
    private DeploymentService deploymentService;

    @Test
    void createDeployment_savesDeployment() {
        CreateDeploymentRequest request = new CreateDeploymentRequest(
                "payment-service",
                Environment.PRODUCTION,
                "1.0.0",
                Status.SUCCESS
        );

        when(deploymentRepository.save(any(Deployment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Deployment result =
                deploymentService.createDeployment(request);

        assertEquals(
                request.applicationName(),
                result.getApplicationName()
        );
        assertEquals(
                request.environment(),
                result.getEnvironment()
        );
        assertEquals(
                request.version(),
                result.getVersion()
        );
        assertEquals(
                request.status(),
                result.getStatus()
        );
        assertNotNull(result.getDeployedAt());

        verify(deploymentRepository).save(result);
    }
}