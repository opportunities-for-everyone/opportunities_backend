package com.project.opportunities.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record DonationResponseDto(
        String status,
        @JsonProperty("order_id")
        String orderId,
        @JsonProperty("amount_credit")
        BigDecimal amountCredit
) {
}
