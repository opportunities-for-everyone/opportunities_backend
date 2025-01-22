package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;
import com.project.opportunities.domain.dto.donation.response.AdditionalPaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.DonationDto;
import com.project.opportunities.domain.dto.donation.response.PaymentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonateService {
    String generateDonationForm(CreateDonateDto donationDto);

    void processDonation(PaymentResponseDto paymentResponseDto,
                         AdditionalPaymentResponseDto additionalPaymentResponseDto);

    Page<DonationDto> getGeneralDonations(Pageable pageable);
}
