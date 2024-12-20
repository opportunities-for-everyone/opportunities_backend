package com.project.opportunities.dto.volunteer;

import com.project.opportunities.model.Volunteer;

public record UpdateVolunteerStatusRequestDto(
        Volunteer.VolunteerStatus status
) {
}
