package com.project.opportunities.service;

import com.project.opportunities.dto.volunteer.CreateVolunteerRequestDto;
import com.project.opportunities.dto.volunteer.UpdateVolunteerStatusRequestDto;
import com.project.opportunities.dto.volunteer.VolunteerResponseDto;
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
