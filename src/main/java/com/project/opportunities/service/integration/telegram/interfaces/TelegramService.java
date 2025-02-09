package com.project.opportunities.service.integration.telegram.interfaces;

import org.springframework.security.core.Authentication;

public interface TelegramService {
    String subscribeNotifications(Authentication authentication);

    String subscribeForNotifications(String chatId, String email);

    String getTotalSubscriptions();

    String removeSubscriber(Long id);
}
