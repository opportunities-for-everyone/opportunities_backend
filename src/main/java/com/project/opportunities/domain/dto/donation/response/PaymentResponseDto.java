package com.project.opportunities.domain.dto.donation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.opportunities.domain.model.Donation;
import java.math.BigDecimal;

public record PaymentResponseDto(
        String status,
        @JsonProperty("amount_credit")
        BigDecimal amount,
        @JsonProperty("sender_phone")
        String donorPhoneNumber,
        @JsonProperty("currency_credit")
        Donation.Currency currency,
        String dae

) {
}
