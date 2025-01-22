package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.partner.request.CreatePartnerRequestDto;
import com.project.opportunities.domain.dto.partner.request.UpdatePartnerStatusRequestDto;
import com.project.opportunities.domain.dto.partner.response.PartnerAllInfoDto;
import com.project.opportunities.domain.dto.partner.response.PartnerGeneralInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    PartnerAllInfoDto addPartner(CreatePartnerRequestDto requestDto);

    Page<PartnerGeneralInfoDto> getPartnersGeneralInfo(Pageable pageable);

    PartnerAllInfoDto getPartnerAllInfo(Long id);

    void submitPartnerApplication(CreatePartnerRequestDto requestDto);

    PartnerAllInfoDto updatePartnerStatus(Long id, UpdatePartnerStatusRequestDto requestDto);

    Page<PartnerAllInfoDto> getPendingPartners(Pageable pageable);

    Page<PartnerAllInfoDto> getAllInfoPartners(Pageable pageable);
}
