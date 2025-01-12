package com.project.opportunities.service;

public interface PaymentCallbackService {
    void processDonationResult(String data, String signature) throws Exception;
}
