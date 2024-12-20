package com.project.opportunities.dto.project;

import com.project.opportunities.model.Project;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectStatusDto(
        @NotNull Project.ProjectStatus status
) {
}
