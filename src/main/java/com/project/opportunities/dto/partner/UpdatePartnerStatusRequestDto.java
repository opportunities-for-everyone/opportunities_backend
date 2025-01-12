package com.project.opportunities.dto.partner;

import com.project.opportunities.model.Partner;

public record UpdatePartnerStatusRequestDto(Partner.PartnerStatus status) {
}
