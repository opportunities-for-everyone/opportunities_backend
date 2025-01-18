package com.project.opportunities.domain.dto.partner.response;

import com.project.opportunities.domain.dto.director.response.DirectorDto;
import com.project.opportunities.domain.model.Partner;

public record PartnerAllInfoDto(Long id,
                                String partnerName,
                                Partner.PartnerType partnerType,
                                String cooperationGoal,
                                String legalAddress,
                                String siteUrl,
                                String identificationCode,
                                DirectorDto director,
                                Partner.PartnerStatus partnerStatus,
                                String logoUrl) {
}
