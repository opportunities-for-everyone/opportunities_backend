package com.project.opportunities.service.impl;

import static com.liqpay.LiqPayUtil.base64_encode;
import static com.liqpay.LiqPayUtil.sha1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.service.DonateService;
import com.project.opportunities.service.PaymentCallbackService;
import com.project.opportunities.service.ProjectService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCallbackServiceImpl implements PaymentCallbackService {
    public static final String SUCCESSFUL_STATUS = "successful";
    private static final String INVALID_SIGNATURE_MESSAGE =
            "Invalid signature: request might be tampered or not authentic.";
    private static final String PARSE_ERROR_MESSAGE = "Could not parse response data";
    private static final String PAYMENT_FAILED_MESSAGE =
            "Your payment was not successful. Check result in your bank application";

    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private final DonateService donateService;

    @Value("${liqpay.private-key}")
    private String privateKey;

    @Value("${liqpay.is-sandbox}")
    private boolean isSandbox;

    @Override
    public void processDonationResult(String data, String signature) {
        checkSignature(data, signature);
        PaymentResponseDto paymentResponseDto = parseData(
                data,
                PaymentResponseDto.class
        );
        AdditionalPaymentResponseDto additionalPaymentResponseDto = parseData(
                paymentResponseDto.dae(),
                AdditionalPaymentResponseDto.class
        );
        checkPaymentStatus(paymentResponseDto);

        switch (additionalPaymentResponseDto.donationType()) {
            case PROJECT_DONATION -> projectService.processDonation(
                    paymentResponseDto,
                    additionalPaymentResponseDto);
            case GENERAL_DONATION -> donateService.processDonation(
                    paymentResponseDto,
                    additionalPaymentResponseDto);
            default -> throw new DonationProcessingException(
                    "Unknown donation type: " + additionalPaymentResponseDto.donationType());
        }
    }

    private <T> T parseData(String data, Class<T> clazz) {
        try {
            String decodedData = new String(
                    Base64.getDecoder().decode(data),
                    StandardCharsets.UTF_8);
            return objectMapper.readValue(decodedData, clazz);
        } catch (JsonProcessingException e) {
            throw new DonationProcessingException(PARSE_ERROR_MESSAGE);
        }
    }

    private void checkPaymentStatus(PaymentResponseDto paymentResponseDto) {
        if (!paymentResponseDto.status().equals(SUCCESSFUL_STATUS) && !isSandbox) {
            throw new DonationProcessingException(PAYMENT_FAILED_MESSAGE);
        }
    }

    private void checkSignature(String data, String signature) {
        String mySignature = base64_encode(sha1(privateKey + data + privateKey));
        if (!mySignature.equals(signature)) {
            throw new DonationProcessingException(INVALID_SIGNATURE_MESSAGE);
        }
    }
}
