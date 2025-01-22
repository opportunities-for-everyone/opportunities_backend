package com.project.opportunities.service.integration.payment.interfaces;

import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;

public interface PaymentGenerationService {

    String getGeneralPaymentForm(CreateDonateDto donationDto);

    String getProjectPaymentForm(CreateDonateDto donationDto, Long donationId);
}
