package com.project.opportunities.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liqpay.LiqPay;
import com.project.opportunities.dto.donation.CreateDonateDto;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.model.Donation;
import com.project.opportunities.service.PaymentGenerationService;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGenerationServiceImpl implements PaymentGenerationService {
    private final LiqPay liqPay;
    private final ObjectMapper objectMapper;

    @Value("${liqpay.callback-url}")
    private String callbackUrl;

    @Override
    public String getGeneralPaymentForm(
            CreateDonateDto donationDto) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "paydonate");
        params.put("amount", donationDto.amount().toEngineeringString());
        params.put("currency", donationDto.currency().getCode());
        params.put("description", "Загальний благодійний внесок");
        params.put("order_id", generateOrderId(Donation.DonationType.GENERAL_DONATION));
        params.put("language", "uk");
        params.put("server_url", callbackUrl);
        params.put("phone", donationDto.donorPhoneNumber());
        params.put("dae", generateAdditionalData(donationDto));
        return liqPay.cnb_form(params);
    }

    @Override
    public String getProjectPaymentForm(
            CreateDonateDto donationDto,
            Long donationId) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "paydonate");
        params.put("amount", donationDto.amount().toEngineeringString());
        params.put("currency", donationDto.currency().getCode());
        params.put("description", "Благодійний внесок на проект ID:%s".formatted(donationId));
        params.put("order_id", generateOrderId(Donation.DonationType.PROJECT_DONATION));
        params.put("language", "uk");
        params.put("server_url", callbackUrl);
        params.put("phone", donationDto.donorPhoneNumber());
        params.put("dae", generateAdditionalData(donationDto, donationId));
        return liqPay.cnb_form(params);
    }

    private String generateAdditionalData(
            CreateDonateDto donationDto) {
        try {
            Map<String, String> additionalData = new HashMap<>();
            additionalData.put("donorName", donationDto.donorName());
            additionalData.put("donorEmail", donationDto.donorEmail());
            additionalData.put("donationType", String.valueOf(
                    Donation.DonationType.GENERAL_DONATION));
            additionalData.put("donationId", "-1");

            String jsonDataAsString = objectMapper.writeValueAsString(additionalData);

            return Base64.getEncoder().encodeToString(jsonDataAsString.getBytes());
        } catch (JsonProcessingException e) {
            throw new DonationProcessingException("Cant generate payment form");
        }
    }

    private String generateAdditionalData(
            CreateDonateDto donationDto,
            Long donationId) {
        try {
            Map<String, String> additionalData = new HashMap<>();
            additionalData.put("donorName", donationDto.donorName());
            additionalData.put("donorEmail", donationDto.donorEmail());
            additionalData.put("donationType", String.valueOf(
                    Donation.DonationType.PROJECT_DONATION));
            additionalData.put("donationId", String.valueOf(donationId));
            String jsonDataAsString = objectMapper.writeValueAsString(additionalData);
            return Base64.getEncoder().encodeToString(jsonDataAsString.getBytes());
        } catch (JsonProcessingException e) {
            throw new DonationProcessingException("Cant generate payment form");
        }

    }

    private String generateOrderId(Donation.DonationType donationType) {
        return donationType.toString() + System.currentTimeMillis();
    }
}
