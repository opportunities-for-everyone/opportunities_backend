package com.project.opportunities.service;

import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.CreateDonateDto;
import com.project.opportunities.dto.donation.DonationDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonateService {
    String generateDonationForm(CreateDonateDto donationDto);

    void processDonation(PaymentResponseDto paymentResponseDto,
                         AdditionalPaymentResponseDto additionalPaymentResponseDto);

    Page<DonationDto> getGeneralDonations(Pageable pageable);
}
