package com.project.opportunities.dto.donation;

import com.project.opportunities.model.Donation;

public record AdditionalPaymentResponseDto(String donorName,
                                           String donorEmail,
                                           Donation.DonationType donationType,
                                           String donationId) {
}
