package com.project.opportunities.dto.project;

import com.project.opportunities.model.Project;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal goalAmount,
        LocalDate deadline,
        Project.ProjectStatus status,
        String imageUrl
) {
}
