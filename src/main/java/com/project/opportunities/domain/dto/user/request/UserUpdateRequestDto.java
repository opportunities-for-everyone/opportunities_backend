package com.project.opportunities.domain.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserUpdateRequestDto(@NotBlank
                                   @Email
                                   @Length(min = 5, max = 50)
                                   String email,
                                   @NotBlank
                                   @Length(max = 30)
                                   String firstName,
                                   @NotBlank
                                   @Length(max = 30)
                                   String lastName,
                                   @NotBlank
                                   @Length(max = 30)
                                   String middleName,
                                   @NotBlank
                                   @Length(max = 100)
                                   String position) {
}
