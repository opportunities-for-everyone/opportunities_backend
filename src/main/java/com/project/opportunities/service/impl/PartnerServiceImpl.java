package com.project.opportunities.service.impl;

import com.project.opportunities.dto.partner.CreatePartnerRequestDto;
import com.project.opportunities.dto.partner.PartnerAllInfoDto;
import com.project.opportunities.dto.partner.PartnerGeneralInfoDto;
import com.project.opportunities.dto.partner.UpdatePartnerStatusRequestDto;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.DirectorMapper;
import com.project.opportunities.mapper.PartnerMapper;
import com.project.opportunities.model.Director;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.Partner;
import com.project.opportunities.repository.PartnerRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerMapper partnerMapper;
    private final DirectorMapper directorMapper;
    private final ImageService imageService;
    private final PartnerRepository partnerRepository;

    @Override
    public PartnerAllInfoDto addPartner(CreatePartnerRequestDto requestDto) {
        Partner partner = createPartner(requestDto, Partner.PartnerStatus.ACTIVE);
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
        createPartner(requestDto, Partner.PartnerStatus.PENDING);
    }

    @Override
    public PartnerAllInfoDto updatePartnerStatus(
            Long id,
            UpdatePartnerStatusRequestDto requestDto) {
        Partner partner = findPartner(id);
        partner.setPartnerStatus(requestDto.status());
        partnerRepository.save(partner);
        return partnerMapper.toAllInfoDto(partner);
    }

    @Override
    public Page<PartnerAllInfoDto> getPendingPartners(Pageable pageable) {
        return partnerRepository.findAllPendingPartners(pageable)
                .map(partnerMapper::toAllInfoDto);
    }

    private Image uploadLogo(MultipartFile file) {
        return imageService.uploadImage(
                file,
                Image.ImageType.PARTNER_AVATAR_IMAGE
        );
    }

    private Partner findPartner(Long id) {
        return partnerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Partner with id " + id + " not found")
        );
    }

    private Partner createPartner(CreatePartnerRequestDto requestDto,
                                  Partner.PartnerStatus partnerStatus) {
        Partner partner = partnerMapper.toPartner(requestDto);
        Image partnerLogo = uploadLogo(requestDto.logo());
        Director director = directorMapper.toDirector(requestDto);

        director.setPartner(partner);
        partner.setDirector(director);
        partner.setLogo(partnerLogo);
        partner.setPartnerStatus(partnerStatus);

        return partnerRepository.save(partner);
    }
}
