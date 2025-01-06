package com.project.opportunities.dto.donation;

import com.project.opportunities.model.Donation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationDto(Long id,
                          BigDecimal amount,
                          Donation.Currency currency,
                          LocalDateTime donationDate,
                          String donorName,
                          String donorEmail) {
}
