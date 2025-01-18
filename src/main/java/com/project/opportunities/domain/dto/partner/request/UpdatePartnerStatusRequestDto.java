package com.project.opportunities.domain.dto.partner.request;

import com.project.opportunities.domain.model.Partner;

public record UpdatePartnerStatusRequestDto(Partner.PartnerStatus status) {
}
