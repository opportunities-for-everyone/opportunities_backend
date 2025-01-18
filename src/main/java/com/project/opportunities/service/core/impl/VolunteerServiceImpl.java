package com.project.opportunities.service.core.impl;

import static com.project.opportunities.utils.UserUtils.getCurrentAdminPanelUser;

import com.project.opportunities.domain.dto.volunteer.request.CreateVolunteerRequestDto;
import com.project.opportunities.domain.dto.volunteer.request.UpdateVolunteerStatusRequestDto;
import com.project.opportunities.domain.dto.volunteer.response.VolunteerResponseDto;
import com.project.opportunities.domain.model.Image;
import com.project.opportunities.domain.model.Volunteer;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.exception.VolunteerApplicationException;
import com.project.opportunities.mapper.VolunteerMapper;
import com.project.opportunities.repository.VolunteerRepository;
import com.project.opportunities.service.core.interfaces.ImageService;
import com.project.opportunities.service.core.interfaces.VolunteerService;
import com.project.opportunities.service.integration.notification.interfaces.NotificationService;
import com.project.opportunities.utils.VolunteerNotificationBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final ImageService imageService;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public VolunteerResponseDto addVolunteer(CreateVolunteerRequestDto requestDto) {
        log.info("Starting to add a new volunteer with email: {}", requestDto.email());

        Volunteer volunteer = volunteerMapper.toModel(requestDto);
        log.debug("Uploading avatar for new volunteer with email: {}", requestDto.email());
        Image volunteerAvatar = imageService.uploadImage(
                requestDto.avatar(),
                Image.ImageType.VOLUNTEER_AVATAR_IMAGE);

        volunteer.setStatus(Volunteer.VolunteerStatus.ACTIVE);
        volunteer.setAvatar(volunteerAvatar);

        log.debug("Saving new volunteer with email: {}", requestDto.email());
        Volunteer saved = volunteerRepository.save(volunteer);

        log.info("Successfully added new volunteer with email: {}", requestDto.email());
        notificationService.sendAdminNotification(
                VolunteerNotificationBuilder
                        .action("Створено нового волонтера")
                        .performer(getCurrentAdminPanelUser())
                        .withVolunteer(volunteer)
                        .build()
        );
        return volunteerMapper.toDto(saved);
    }

    @Override
    public VolunteerResponseDto submitApplication(CreateVolunteerRequestDto requestDto) {
        log.info("Processing volunteer application for email: {}", requestDto.email());

        if (volunteerRepository.existsByEmailOrPhoneNumber(
                requestDto.email(), requestDto.phoneNumber())) {
            log.warn("Volunteer already exists with email: {} or phone number: {}",
                    requestDto.email(), requestDto.phoneNumber());
            throw new VolunteerApplicationException(
                    "The application or volunteer already exists."
            );
        }

        Volunteer volunteer = volunteerMapper.toModel(requestDto);
        log.debug("Uploading avatar for volunteer application with email: {}", requestDto.email());
        Image volunteerAvatar = imageService.uploadImage(
                requestDto.avatar(),
                Image.ImageType.VOLUNTEER_AVATAR_IMAGE
        );

        volunteer.setAvatar(volunteerAvatar);
        volunteer.setStatus(Volunteer.VolunteerStatus.PENDING);

        log.debug("Saving volunteer application for email: {}", requestDto.email());
        Volunteer saved = volunteerRepository.save(volunteer);

        log.info("Successfully submitted volunteer application for email: {}", requestDto.email());

        notificationService.sendAdminNotification(
                VolunteerNotificationBuilder
                        .action("Нова заявка на волонтерство")
                        .withVolunteer(volunteer)
                        .build()
        );
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
        log.info("Updating volunteer status for volunteer ID: {}", id);

        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> {
            log.error("Volunteer not found by ID: {}", id);
            return new EntityNotFoundException("Volunteer not found by ID: " + id);
        });
        volunteer.setStatus(requestDto.status());

        log.debug("Saving updated status for volunteer ID: {}", id);
        Volunteer saved = volunteerRepository.save(volunteer);

        log.info("Successfully updated status for volunteer ID: {}", id);

        notificationService.sendAdminNotification(
                VolunteerNotificationBuilder
                        .action("Оновлено статус партнера")
                        .performer(getCurrentAdminPanelUser())
                        .withVolunteer(volunteer)
                        .build()
        );
        return volunteerMapper.toDto(saved);
    }

    @Override
    public Page<VolunteerResponseDto> getRejectedVolunteers(Pageable pageable) {
        return volunteerRepository.findAllByStatus_Rejected(pageable)
                .map(volunteerMapper::toDto);
    }
}
