package com.project.opportunities.domain.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Email
        @NotBlank
        @Size(min = 9, max = 50)
        String email,
        @NotBlank
        @Size(min = 8)
        String password
) {
}
