package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.volunteer.request.CreateVolunteerRequestDto;
import com.project.opportunities.domain.dto.volunteer.request.UpdateVolunteerStatusRequestDto;
import com.project.opportunities.domain.dto.volunteer.response.VolunteerResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VolunteerService {
    VolunteerResponseDto addVolunteer(@Valid CreateVolunteerRequestDto requestDto);

    VolunteerResponseDto submitApplication(@Valid CreateVolunteerRequestDto requestDto);

    Page<VolunteerResponseDto> getActiveVolunteers(Pageable pageable);

    Page<VolunteerResponseDto> getPendingVolunteers(Pageable pageable);

    VolunteerResponseDto updateVolunteerStatus(Long id, UpdateVolunteerStatusRequestDto requestDto);

    Page<VolunteerResponseDto> getRejectedVolunteers(Pageable pageable);
}
