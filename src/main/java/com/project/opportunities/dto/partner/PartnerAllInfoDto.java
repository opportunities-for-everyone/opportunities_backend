package com.project.opportunities.dto.partner;

import com.project.opportunities.dto.director.DirectorDto;
import com.project.opportunities.model.Partner;

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
