package com.project.opportunities.config;

import com.liqpay.LiqPay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiqPayConfig {
    @Value("${liqpay.public-key}")
    private String publicKey;
    @Value("${liqpay.private-key}")
    private String privateKey;
    @Value("${liqpay.is-sandbox}")
    private boolean isSandbox;

    @Bean
    public LiqPay liqPay() {
        LiqPay liqPay = new LiqPay(publicKey, privateKey);
        liqPay.setCnbSandbox(isSandbox);
        return liqPay;
    }
}
