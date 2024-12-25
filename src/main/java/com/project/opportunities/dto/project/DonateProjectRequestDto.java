package com.project.opportunities.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DonateProjectRequestDto(
        @NotNull
        BigDecimal amount,
        @NotBlank
        String currency
) {
}
