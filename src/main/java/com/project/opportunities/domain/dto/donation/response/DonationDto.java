package com.project.opportunities.domain.dto.donation.response;

import com.project.opportunities.domain.model.Donation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationDto(Long id,
                          BigDecimal amount,
                          Donation.Currency currency,
                          LocalDateTime donationDate,
                          String donorName,
                          String donorEmail) {
}
