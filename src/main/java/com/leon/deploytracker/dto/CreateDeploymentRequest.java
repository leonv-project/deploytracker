package com.leon.deploytracker.dto;
import com.leon.deploytracker.model.Environment;
import com.leon.deploytracker.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeploymentRequest(
        @NotBlank String applicationName,
        @NotNull Environment environment,
        @NotBlank String version,
        @NotNull Status status
) {
}