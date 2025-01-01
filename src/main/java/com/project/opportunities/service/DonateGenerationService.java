package com.project.opportunities.service;

public interface DonateGenerationService {
    String generatePaymentForm(double amount,
                               String currency,
                               String description,
                               String donationId);
}
