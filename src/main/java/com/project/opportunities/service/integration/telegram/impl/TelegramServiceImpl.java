package com.project.opportunities.service.integration.telegram.impl;

import com.project.opportunities.domain.model.TelegramChat;
import com.project.opportunities.domain.model.User;
import com.project.opportunities.repository.TelegramChatRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.integration.telegram.interfaces.TelegramService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final UserRepository userRepository;
    private final TelegramChatRepository telegramChatRepository;

    @Value("${telegram.bot.url}")
    private String telegramBotUrl;

    @Override
    public String subscribeNotifications(Authentication authentication) {
        userRepository.existsByEmail(authentication.getName());
        return telegramBotUrl;
    }

    @Override
    @Transactional
    public String subscribeForNotifications(String chatId, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return email + " is not registered as an admin or editor.Check your email address";
        }
        if (telegramChatRepository.existsByChatId(chatId)) {
            return chatId + " is already subscribed for notifications";
        }

        User user = userOptional.get();
        TelegramChat telegramChat = new TelegramChat();
        telegramChat.setChatId(chatId);
        telegramChat.setUser(user);

        telegramChatRepository.save(telegramChat);

        return "You are successfully subscribed for notifications";
    }

}
