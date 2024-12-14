package com.project.opportunities.dto.user;

public record UserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String middleName,
        String photoUrl
) {
}
