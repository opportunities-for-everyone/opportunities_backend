package com.project.opportunities.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.opportunities.model.Donation;
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
