package com.project.opportunities.domain.dto.volunteer.request;

import com.project.opportunities.domain.model.Volunteer;

public record UpdateVolunteerStatusRequestDto(
        Volunteer.VolunteerStatus status
) {
}
