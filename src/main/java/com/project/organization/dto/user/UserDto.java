package com.project.organization.dto.user;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String middleName,
        String photoUrl
) {
}
