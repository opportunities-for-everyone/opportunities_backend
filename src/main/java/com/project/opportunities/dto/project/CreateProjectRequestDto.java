package com.project.opportunities.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateProjectRequestDto(
        @NotBlank
        @Length(min = 10, max = 100)
        String name,
        @NotBlank
        String description,
        @NotNull
        BigDecimal goalAmount,
        @NotNull
        LocalDate deadline,
        @NotNull
        MultipartFile image
) {
}
