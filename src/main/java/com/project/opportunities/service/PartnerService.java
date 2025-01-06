package com.project.opportunities.service;

import com.project.opportunities.dto.partner.CreatePartnerRequestDto;
import com.project.opportunities.dto.partner.PartnerAllInfoDto;
import com.project.opportunities.dto.partner.PartnerGeneralInfoDto;
import com.project.opportunities.dto.partner.UpdatePartnerStatusRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    PartnerAllInfoDto addPartner(CreatePartnerRequestDto requestDto);

    Page<PartnerGeneralInfoDto> getPartnersGeneralInfo(Pageable pageable);

    PartnerAllInfoDto getPartnerAllInfo(Long id);

    void submitPartnerApplication(CreatePartnerRequestDto requestDto);

    PartnerAllInfoDto updatePartnerStatus(Long id, UpdatePartnerStatusRequestDto requestDto);

    Page<PartnerAllInfoDto> getPendingPartners(Pageable pageable);
}
