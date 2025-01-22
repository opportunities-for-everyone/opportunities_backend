package com.project.opportunities.domain.dto.email;

import com.project.opportunities.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AllowedEmailCreateDto(@NotBlank
                                    @Email
                                    String email,
                                    @NotNull
                                    Role.RoleName roleName) {
}
