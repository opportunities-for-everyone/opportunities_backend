package com.project.opportunities.dto.volunteer;

import com.project.opportunities.model.Volunteer;

public record VolunteerResponseDto(
        Long id,
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String email,
        Volunteer.VolunteerStatus status,
        String avatarUrl
) {
}
