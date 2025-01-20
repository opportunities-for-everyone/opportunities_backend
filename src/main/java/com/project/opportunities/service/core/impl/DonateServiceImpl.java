package com.project.opportunities.service.core.impl;

import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;
import com.project.opportunities.domain.dto.donation.response.AdditionalPaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.DonationDto;
import com.project.opportunities.domain.dto.donation.response.PaymentResponseDto;
import com.project.opportunities.domain.model.Donation;
import com.project.opportunities.mapper.DonationMapper;
import com.project.opportunities.repository.DonationRepository;
import com.project.opportunities.service.core.interfaces.DonateService;
import com.project.opportunities.service.integration.notification.interfaces.NotificationService;
import com.project.opportunities.service.integration.payment.interfaces.PaymentGenerationService;
import com.project.opportunities.utils.notification.DonationNotificationBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonateServiceImpl implements DonateService {
    private final PaymentGenerationService paymentGenerationService;
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final NotificationService notificationService;

    @Override
    public String generateDonationForm(CreateDonateDto donationDto) {
        log.info("Generating donation form for donation: {}", donationDto);
        return paymentGenerationService.getGeneralPaymentForm(donationDto);
    }

    @Override
    @Transactional
    public void processDonation(PaymentResponseDto paymentResponseDto,
                                AdditionalPaymentResponseDto additionalPaymentResponseDto) {
        log.info("Processing donation. Payment response: {}, Additional payment response: {}",
                paymentResponseDto, additionalPaymentResponseDto);
        Donation donation = donationMapper.toGeneralDonation(
                paymentResponseDto,
                additionalPaymentResponseDto
        );
        donationRepository.save(donation);
        log.info("Successfully processed and saved donation: {}", donation);

        notificationService.sendNotificationToAdmin(
                DonationNotificationBuilder
                        .action("Новий загальний донат")
                        .withEntity(donation)
                        .build()
        );
    }

    @Override
    public Page<DonationDto> getGeneralDonations(Pageable pageable) {
        return donationRepository.findAll(pageable).map(donationMapper::toGeneralDonationDto);
    }
}
