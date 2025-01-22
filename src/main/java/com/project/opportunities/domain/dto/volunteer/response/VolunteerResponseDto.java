package com.project.opportunities.domain.dto.volunteer.response;

import com.project.opportunities.domain.model.Volunteer;

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
