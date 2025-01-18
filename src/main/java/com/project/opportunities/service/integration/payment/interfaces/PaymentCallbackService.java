package com.project.opportunities.service.integration.payment.interfaces;

public interface PaymentCallbackService {
    void processDonationResult(String data, String signature) throws Exception;
}
