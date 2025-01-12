package com.project.opportunities.service;

import com.project.opportunities.dto.donation.CreateDonateDto;

public interface PaymentGenerationService {

    String getGeneralPaymentForm(CreateDonateDto donationDto);

    String getProjectPaymentForm(CreateDonateDto donationDto, Long donationId);
}
