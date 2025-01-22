package com.project.opportunities.domain.dto.donation.response;

import com.project.opportunities.domain.dto.project.response.ProjectResponseDto;
import com.project.opportunities.domain.model.Donation;
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
