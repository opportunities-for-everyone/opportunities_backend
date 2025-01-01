package com.project.opportunities.service.impl;

import static com.liqpay.LiqPayUtil.base64_encode;
import static com.liqpay.LiqPayUtil.sha1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.opportunities.dto.donation.DonationResponseDto;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.service.DonateCallbackService;
import com.project.opportunities.service.ProjectService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonateCallbackServiceImpl implements DonateCallbackService {
    public static final String DONATION_TYPE_PROJECT = "projectID";
    public static final String SUCCESSFUL_STATUS = "successful";
    public static final int DONATION_TYPE = 0;
    private static final int DONATION_ID = 1;

    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    @Value("${liqpay.private-key}")
    private String privateKey;

    @Value("${liqpay.is-sandbox}")
    private boolean isSandbox;

    @Override
    public void processDonationResult(String data, String signature) {
        String mySignature = generateSignature(data);
        if (!mySignature.equals(signature)) {
            throw new DonationProcessingException(
                    "Invalid signature: request might be tampered or not authentic."
            );
        }
        DonationResponseDto donationResponseDto = parseData(data);
        checkPaymentStatus(donationResponseDto);
        String[] donationData = donationResponseDto.orderId().split("_");
        Long donationId = parseProjectId(donationData);

        if (donationData[DONATION_TYPE].equals(DONATION_TYPE_PROJECT)) {
            projectService.updateCollectedAmount(donationId, donationResponseDto.amountCredit());
        }
        //here in future i will add support for other types od donate, so i wil refactor it
    }

    private String generateSignature(String data) {
        return base64_encode(sha1(privateKey + data + privateKey));
    }

    private Long parseProjectId(String[] data) {
        return Long.valueOf(data[DONATION_ID]);
    }

    private DonationResponseDto parseData(String data) {
        String decodedData = new String(
                Base64.getDecoder().decode(data),
                StandardCharsets.UTF_8);

        try {
            return objectMapper.readValue(decodedData, DonationResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new DonationProcessingException("Could not parse response data from LiqPay");
        }

    }

    private void checkPaymentStatus(DonationResponseDto donationResponseDto) {
        if (!donationResponseDto.status().equals(SUCCESSFUL_STATUS) && !isSandbox) {
            throw new DonationProcessingException(
                    "Your payment was not successful.Check result in your bank application");
        }
    }
}
