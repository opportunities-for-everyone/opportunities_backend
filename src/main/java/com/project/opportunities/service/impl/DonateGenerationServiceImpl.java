package com.project.opportunities.service.impl;

import com.liqpay.LiqPay;
import com.project.opportunities.service.DonateGenerationService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonateGenerationServiceImpl implements DonateGenerationService {
    private final LiqPay liqPay;

    @Value("${liqpay.callback-url}")
    private String callbackUrl;

    @Override
    public String generatePaymentForm(double amount,
                                      String currency,
                                      String description,
                                      String donationId) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", String.valueOf(amount));
        params.put("currency", currency);
        params.put("description", description);
        params.put("order_id", donationId);
        params.put("language", "uk");
        params.put("server_url", callbackUrl);
        return liqPay.cnb_form(params);
    }
}
