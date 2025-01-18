package com.project.opportunities.service.integration.payment.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liqpay.LiqPay;
import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;
import com.project.opportunities.domain.model.Donation;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.service.integration.payment.interfaces.PaymentGenerationService;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentGenerationServiceImpl implements PaymentGenerationService {
    private final LiqPay liqPay;
    private final ObjectMapper objectMapper;

    @Value("${liqpay.callback-url}")
    private String callbackUrl;

    @Override
    public String getGeneralPaymentForm(CreateDonateDto donationDto) {
        log.info("Generating general donation payment form for donor: {}", donationDto.donorName());
        Map<String, String> params = prepareCommonParams(
                donationDto, Donation.DonationType.GENERAL_DONATION);
        params.put("description", "Загальний благодійний внесок");
        params.put("dae", generateAdditionalData(donationDto, Optional.empty()));
        String form = liqPay.cnb_form(params);
        log.info("General payment form generated successfully.");
        return form;
    }

    @Override
    public String getProjectPaymentForm(CreateDonateDto donationDto, Long donationId) {
        log.info("Generating project donation payment form for donor: {}, project ID: {}",
                donationDto.donorName(), donationId);
        Map<String, String> params = prepareCommonParams(
                donationDto, Donation.DonationType.PROJECT_DONATION);
        params.put("description", "Благодійний внесок на проект ID:%s".formatted(donationId));
        params.put("dae", generateAdditionalData(donationDto, Optional.of(donationId)));
        String form = liqPay.cnb_form(params);
        log.info("Project payment form generated successfully for project ID: {}", donationId);
        return form;
    }

    private Map<String, String> prepareCommonParams(CreateDonateDto donationDto,
                                                    Donation.DonationType donationType) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "paydonate");
        params.put("amount", donationDto.amount().toEngineeringString());
        params.put("currency", donationDto.currency().getCode());
        params.put("order_id", generateOrderId(donationType));
        params.put("language", "uk");
        params.put("server_url", callbackUrl);
        params.put("phone", donationDto.donorPhoneNumber());
        return params;
    }

    private String generateAdditionalData(CreateDonateDto donationDto,
                                          Optional<Long> donationId) {
        try {
            log.info("Generating additional data for donor: {}, donation ID: {}",
                    donationDto.donorName(), donationId.orElse(-1L));
            Map<String, String> additionalData = new HashMap<>();
            additionalData.put("donorName", donationDto.donorName());
            additionalData.put("donorEmail", donationDto.donorEmail());
            additionalData.put("donationType", donationId.isPresent()
                    ? Donation.DonationType.PROJECT_DONATION.toString()
                    : Donation.DonationType.GENERAL_DONATION.toString());
            additionalData.put("donationId", String.valueOf(donationId.orElse(-1L)));

            String jsonDataAsString = objectMapper.writeValueAsString(additionalData);
            String encodedData = Base64.getEncoder().encodeToString(jsonDataAsString.getBytes());
            log.info("Additional data generated successfully: {}", additionalData);
            return encodedData;
        } catch (JsonProcessingException e) {
            log.error("Failed to generate additional data for donation.", e);
            throw new DonationProcessingException(
                    "Unable to generate payment form due to additional data encoding error.");
        }
    }

    private String generateOrderId(Donation.DonationType donationType) {
        String orderId = donationType.toString() + System.currentTimeMillis();
        log.info("Generated order ID: {}", orderId);
        return orderId;
    }
}
