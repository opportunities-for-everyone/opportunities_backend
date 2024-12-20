package com.project.opportunities.service.impl;

import com.project.opportunities.dto.volunteer.CreateVolunteerRequestDto;
import com.project.opportunities.dto.volunteer.UpdateVolunteerStatusRequestDto;
import com.project.opportunities.dto.volunteer.VolunteerResponseDto;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.exception.VolunteerApplicationException;
import com.project.opportunities.mapper.VolunteerMapper;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.Volunteer;
import com.project.opportunities.repository.VolunteerRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.VolunteerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final ImageService imageService;

    @Transactional
    @Override
    public VolunteerResponseDto addVolunteer(CreateVolunteerRequestDto requestDto) {
        Volunteer volunteer = volunteerMapper.toModel(requestDto);
        Image volunteerAvatar = imageService.uploadImage(
                requestDto.avatar(),
                Image.ImageType.VOLUNTEER_AVATAR_IMAGE);

        volunteer.setStatus(Volunteer.VolunteerStatus.ACTIVE);
        volunteer.setAvatar(volunteerAvatar);
        Volunteer saved = volunteerRepository.save(volunteer);
        return volunteerMapper.toDto(saved);
    }

    @Override
    public VolunteerResponseDto submitApplication(CreateVolunteerRequestDto requestDto) {
        if (volunteerRepository.existsByEmailOrPhoneNumber(
                requestDto.email(), requestDto.phoneNumber())) {
            throw new VolunteerApplicationException(
                    "The application or volunteer already exists."
            );
        }

        Volunteer volunteer = volunteerMapper.toModel(requestDto);
        Image volunteerAvatar = imageService.uploadImage(
                requestDto.avatar(),
                Image.ImageType.VOLUNTEER_AVATAR_IMAGE
        );

        volunteer.setAvatar(volunteerAvatar);
        volunteer.setStatus(Volunteer.VolunteerStatus.PENDING);
        Volunteer saved = volunteerRepository.save(volunteer);
        return volunteerMapper.toDto(saved);
    }

    @Override
    public Page<VolunteerResponseDto> getActiveVolunteers(Pageable pageable) {
        return volunteerRepository.findAllByStatus_Active(pageable)
                .map(volunteerMapper::toDto);
    }

    @Override
    public Page<VolunteerResponseDto> getPendingVolunteers(Pageable pageable) {
        return volunteerRepository.findAllByStatus_Pending(pageable)
                .map(volunteerMapper::toDto);
    }

    @Override
    public VolunteerResponseDto updateVolunteerStatus(
            Long id,
            UpdateVolunteerStatusRequestDto requestDto) {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Volunteer not found with id: " + id));
        volunteer.setStatus(requestDto.status());
        Volunteer saved = volunteerRepository.save(volunteer);
        return volunteerMapper.toDto(saved);
    }

    @Override
    public Page<VolunteerResponseDto> getRejectedVolunteers(Pageable pageable) {
        return volunteerRepository.findAllByStatus_Rejected(pageable)
                .map(volunteerMapper::toDto);
    }
}
