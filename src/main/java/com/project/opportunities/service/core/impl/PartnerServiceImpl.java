package com.project.opportunities.service.core.impl;

import static com.project.opportunities.utils.UserUtils.getCurrentAdminPanelUser;

import com.project.opportunities.domain.dto.partner.request.CreatePartnerRequestDto;
import com.project.opportunities.domain.dto.partner.request.UpdatePartnerStatusRequestDto;
import com.project.opportunities.domain.dto.partner.response.PartnerAllInfoDto;
import com.project.opportunities.domain.dto.partner.response.PartnerGeneralInfoDto;
import com.project.opportunities.domain.model.Director;
import com.project.opportunities.domain.model.Image;
import com.project.opportunities.domain.model.Partner;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.DirectorMapper;
import com.project.opportunities.mapper.PartnerMapper;
import com.project.opportunities.repository.PartnerRepository;
import com.project.opportunities.service.core.interfaces.ImageService;
import com.project.opportunities.service.core.interfaces.PartnerService;
import com.project.opportunities.service.integration.notification.interfaces.NotificationService;
import com.project.opportunities.utils.notification.PartnerNotificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerServiceImpl implements PartnerService {
    private final PartnerMapper partnerMapper;
    private final DirectorMapper directorMapper;
    private final ImageService imageService;
    private final PartnerRepository partnerRepository;
    private final NotificationService notificationService;

    @Override
    public PartnerAllInfoDto addPartner(CreatePartnerRequestDto requestDto) {
        log.info("Creating a new partner with status: ACTIVE");
        Partner partner = createPartner(requestDto, Partner.PartnerStatus.ACTIVE);
        log.info("Partner created successfully with ID: {}", partner.getId());
        notificationService.sendNotificationToAdmins(
                PartnerNotificationBuilder
                        .action("Створено нового партнера")
                        .performer(getCurrentAdminPanelUser())
                        .withEntity(partner)
                        .build()
        );
        return partnerMapper.toAllInfoDto(partner);
    }

    @Override
    public Page<PartnerGeneralInfoDto> getPartnersGeneralInfo(Pageable pageable) {
        return partnerRepository.findAllActivePartners(pageable)
                .map(partnerMapper::toGeneralInfoDto);
    }

    @Override
    public PartnerAllInfoDto getPartnerAllInfo(Long id) {
        return partnerMapper.toAllInfoDto(findPartner(id));
    }

    @Override
    public void submitPartnerApplication(CreatePartnerRequestDto requestDto) {
        log.info("Submitting a new partner application with status: PENDING");
        Partner partner = createPartner(requestDto, Partner.PartnerStatus.PENDING);
        notificationService.sendNotificationToAdmins(
                PartnerNotificationBuilder
                        .action("Нова заявка на партнерство")
                        .withEntity(partner)
                        .build()
        );
        log.info("Partner application submitted successfully with ID: {}", partner.getId());
    }

    @Override
    public PartnerAllInfoDto updatePartnerStatus(
            Long id,
            UpdatePartnerStatusRequestDto requestDto) {
        log.info("Updating status for partner with ID: {} to {}", id, requestDto.status());
        Partner partner = findPartner(id);
        partner.setPartnerStatus(requestDto.status());
        partnerRepository.save(partner);
        log.info("Partner status updated successfully for ID: {}", id);
        notificationService.sendNotificationToAdmins(
                PartnerNotificationBuilder
                        .action("Оновлено статус партнера")
                        .performer(getCurrentAdminPanelUser())
                        .withEntity(partner)
                        .build()
        );
        return partnerMapper.toAllInfoDto(partner);
    }

    @Override
    public Page<PartnerAllInfoDto> getPendingPartners(Pageable pageable) {
        return partnerRepository.findAllPendingPartners(pageable)
                .map(partnerMapper::toAllInfoDto);
    }

    @Override
    public Page<PartnerAllInfoDto> getAllInfoPartners(Pageable pageable) {
        return partnerRepository.findAllWithDirectorAndLogo(pageable)
                .map(partnerMapper::toAllInfoDto);
    }

    private Image uploadLogo(MultipartFile file) {
        return imageService.uploadImage(
                file,
                Image.ImageType.PARTNER_AVATAR_IMAGE
        );
    }

    private Partner findPartner(Long id) {
        return partnerRepository.findById(id).orElseThrow(() -> {
            log.error("Partner not found with ID: {}", id);
            return new EntityNotFoundException("Partner with id " + id + " not found");
        });
    }

    private Partner createPartner(CreatePartnerRequestDto requestDto,
                                  Partner.PartnerStatus partnerStatus) {
        log.info("Creating partner with name: {}, status: {}",
                requestDto.partnerName(), partnerStatus);
        Partner partner = partnerMapper.toPartner(requestDto);
        Image partnerLogo = uploadLogo(requestDto.logo());
        Director director = directorMapper.toDirector(requestDto);

        director.setPartner(partner);
        partner.setDirector(director);
        partner.setLogo(partnerLogo);
        partner.setPartnerStatus(partnerStatus);

        Partner savedPartner = partnerRepository.save(partner);
        log.info("Partner created and saved to the database with ID: {}", savedPartner.getId());
        return savedPartner;
    }
}
