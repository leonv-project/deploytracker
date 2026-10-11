package com.leon.deploytracker.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

import com.leon.deploytracker.dto.CreateDeploymentRequest;
import com.leon.deploytracker.model.Deployment;
import com.leon.deploytracker.model.Environment;
import com.leon.deploytracker.model.Status;
import com.leon.deploytracker.service.DeploymentService;


@WebMvcTest(DeploymentController.class)
class DeploymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeploymentService deploymentService;

    @Test
    void getAllDeployments_returnsEmptyList() throws Exception {
            when(deploymentService.getAllDeployments())
            .thenReturn(List.of());

            mockMvc.perform(get("/deployments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

    verify(deploymentService).getAllDeployments();
    }

    @Test
    void getDeploymentById_returnsDeploymentWhenFound() throws Exception{
        Deployment deployment = createDeployment();
        
        when(deploymentService.getDeploymentById(1L))
        .thenReturn(Optional.of(deployment));

        mockMvc.perform(get("/deployments/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.applicationName")
               .value("payment-service"));

        verify(deploymentService).getDeploymentById(1L);
    }

    @Test
    void getDeploymentById_returnsNotFoundWhenMissing() throws Exception {
        when(deploymentService.getDeploymentById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/deployments/99"))
                .andExpect(status().isNotFound());

        verify(deploymentService).getDeploymentById(99L);
    }

    @Test
    void createDeployment_returnsCreatedDeployment() throws Exception {
        Deployment deployment = createDeployment();

        when(deploymentService.createDeployment(any(CreateDeploymentRequest.class)))
                .thenReturn(deployment);

        mockMvc.perform(post("/deployments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                    "applicationName": "payment-service",
                    "environment": "PRODUCTION",
                    "version": "1.0.0",
                    "status": "SUCCESS"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.applicationName").value("payment-service"));

        verify(deploymentService)
                .createDeployment(any(CreateDeploymentRequest.class));
        }

    @Test
void createDeployment_returnsBadRequestForInvalidBody() throws Exception {
    mockMvc.perform(post("/deployments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "applicationName": "",
                  "environment": "PRODUCTION",
                  "version": "1.0.0",
                  "status": "SUCCESS"
                }
                """))
            .andExpect(status().isBadRequest());

    verify(deploymentService, never())
            .createDeployment(any(CreateDeploymentRequest.class));
}


    // Shared helper used by multiple tests
    private Deployment createDeployment() {
        Deployment deployment = new Deployment();

        deployment.setApplicationName("payment-service");
        deployment.setEnvironment(Environment.PRODUCTION);
        deployment.setVersion("1.0.0");
        deployment.setStatus(Status.SUCCESS);
        deployment.setDeployedAt(
                LocalDateTime.of(2026, 10, 8, 12, 0)
        );

        ReflectionTestUtils.setField(deployment, "id", 1L);

        return deployment;
    }
}



