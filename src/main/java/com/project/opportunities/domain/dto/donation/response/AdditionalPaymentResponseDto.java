package com.project.opportunities.domain.dto.donation.response;

import com.project.opportunities.domain.model.Donation;

public record AdditionalPaymentResponseDto(String donorName,
                                           String donorEmail,
                                           Donation.DonationType donationType,
                                           String donationId) {
}
