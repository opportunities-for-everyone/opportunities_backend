package com.project.opportunities.domain.dto.project.response;

import com.project.opportunities.domain.model.Project;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal goalAmount,
        BigDecimal collectedAmount,
        LocalDate deadline,
        Project.ProjectStatus status,
        String imageUrl
) {
}
