package com.project.opportunities.service;

public interface DonateCallbackService {
    void processDonationResult(String data, String signature) throws Exception;
}
