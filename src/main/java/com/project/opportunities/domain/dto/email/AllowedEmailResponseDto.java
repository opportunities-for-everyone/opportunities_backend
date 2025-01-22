package com.project.opportunities.domain.dto.email;

import com.project.opportunities.domain.model.Role;
import java.time.LocalDateTime;

public record AllowedEmailResponseDto(Long id,
                                      String email,
                                      Role.RoleName roleName,
                                      LocalDateTime createdAt) {
}
