package com.project.opportunities.dto.donation;

import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.model.Donation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectDonationDto(Long id,
                                 BigDecimal amount,
                                 Donation.Currency currency,
                                 LocalDateTime donationDate,
                                 String donorName,
                                 String donorEmail,
                                 ProjectResponseDto project) {
}
