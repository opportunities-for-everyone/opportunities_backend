package com.project.opportunities.service.impl;

import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.CreateDonateDto;
import com.project.opportunities.dto.donation.DonationDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import com.project.opportunities.mapper.DonationMapper;
import com.project.opportunities.model.Donation;
import com.project.opportunities.repository.DonationRepository;
import com.project.opportunities.service.DonateService;
import com.project.opportunities.service.PaymentGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonateServiceImpl implements DonateService {
    private final PaymentGenerationService paymentGenerationService;
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;

    @Override
    public String generateDonationForm(CreateDonateDto donationDto) {
        return paymentGenerationService.getGeneralPaymentForm(donationDto);
    }

    @Override
    public void processDonation(PaymentResponseDto paymentResponseDto,
                                AdditionalPaymentResponseDto additionalPaymentResponseDto) {
        Donation donation = donationMapper.toGeneralDonation(
                paymentResponseDto,
                additionalPaymentResponseDto
        );
        donationRepository.save(donation);
    }

    @Override
    public Page<DonationDto> getGeneralDonations(Pageable pageable) {
        return donationRepository.findAll(pageable).map(donationMapper::toGeneralDonationDto);
    }
}
