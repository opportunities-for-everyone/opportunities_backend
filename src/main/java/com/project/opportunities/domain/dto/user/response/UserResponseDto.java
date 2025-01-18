package com.project.opportunities.domain.dto.user.response;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String middleName,
        String photoUrl
) {
}
