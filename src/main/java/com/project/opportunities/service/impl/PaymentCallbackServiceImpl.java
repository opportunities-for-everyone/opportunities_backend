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
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCallbackServiceImpl implements PaymentCallbackService {
    public static final String SUCCESSFUL_STATUS = "successful";
    private static final String INVALID_SIGNATURE_MESSAGE =
            "Invalid signature: request might be tampered or not authentic.";
    private static final String PARSE_ERROR_MESSAGE = "Could not parse response data";
    private static final String PAYMENT_FAILED_MESSAGE =
            "Your payment was not successful. Check result in your bank application";
    private static final String DONATION_TYPE_ERROR = "Unknown donation type: ";

    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private final DonateService donateService;

    @Value("${liqpay.private-key}")
    private String privateKey;

    @Value("${liqpay.is-sandbox}")
    private boolean isSandbox;

    @Override
    @Transactional
    public void processDonationResult(String data, String signature) {
        MDC.put("paymentSignature", signature.substring(0, 8));

        try {
            log.info("Processing donation payment callback [dataLength={}]", data.length());

            checkSignature(data, signature);
            PaymentResponseDto paymentResponse = parseData(data, PaymentResponseDto.class);
            MDC.put("amount", paymentResponse.amount().toString());

            AdditionalPaymentResponseDto additionalResponse =
                    parseData(paymentResponse.dae(), AdditionalPaymentResponseDto.class);

            MDC.put("donationType", additionalResponse.donationType().name());
            MDC.put("donationId", additionalResponse.donationId());
            MDC.put("donationId", additionalResponse.donationId());

            checkPaymentStatus(paymentResponse);

            switch (additionalResponse.donationType()) {
                case PROJECT_DONATION -> {
                    log.info("Processing project donation [projectId={}]",
                            additionalResponse.donationId());
                    projectService.processDonation(paymentResponse, additionalResponse);
                }
                case GENERAL_DONATION -> {
                    log.info("Processing general donation");
                    donateService.processDonation(paymentResponse, additionalResponse);
                }
                default -> {
                    log.error("Invalid donation type [type={}]", additionalResponse.donationType());
                    throw new DonationProcessingException(
                            DONATION_TYPE_ERROR + additionalResponse.donationType());
                }
            }

            log.info("Successfully processed donation payment");

        } catch (Exception e) {
            log.error("Failed to process donation payment", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    private <T> T parseData(String data, Class<T> clazz) {
        try {
            String decodedData = new String(
                    Base64.getDecoder().decode(data),
                    StandardCharsets.UTF_8);

            log.debug("Parsing payment data [class={}]", clazz.getSimpleName());
            return objectMapper.readValue(decodedData, clazz);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse payment data [class={}]", clazz.getSimpleName(), e);
            throw new DonationProcessingException(PARSE_ERROR_MESSAGE);
        }
    }

    private void checkPaymentStatus(PaymentResponseDto paymentResponseDto) {
        if (!paymentResponseDto.status().equals(SUCCESSFUL_STATUS) && !isSandbox) {
            log.error("Payment validation failed [status={}, sandbox={}]",
                    paymentResponseDto.status(), isSandbox);
            throw new DonationProcessingException(PAYMENT_FAILED_MESSAGE);
        }
        log.debug("Payment status validated [status={}]", paymentResponseDto.status());
    }

    private void checkSignature(String data, String signature) {
        String calculatedSignature = base64_encode(sha1(privateKey + data + privateKey));

        if (!calculatedSignature.equals(signature)) {
            log.error("Invalid payment signature [expected={}, received={}]",
                    calculatedSignature.substring(0, 8),
                    signature.substring(0, 8));
            throw new DonationProcessingException(INVALID_SIGNATURE_MESSAGE);
        }
        log.debug("Payment signature validated");
    }
}
