package com.project.opportunities.domain.dto.project.request;

import com.project.opportunities.domain.model.Project;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectStatusDto(
        @NotNull Project.ProjectStatus status
) {
}
