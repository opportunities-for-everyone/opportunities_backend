package com.project.opportunities.service.integration.telegram.impl;

import com.project.opportunities.domain.model.TelegramChat;
import com.project.opportunities.domain.model.User;
import com.project.opportunities.exception.AlreadySubscribedException;
import com.project.opportunities.exception.InvalidSubscriptionException;
import com.project.opportunities.repository.TelegramChatRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.integration.telegram.interfaces.TelegramService;
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
        try {
            User user = validateAndGetUser(email);
            validateChatNotSubscribed(chatId);

            TelegramChat telegramChat = new TelegramChat();
            telegramChat.setChatId(chatId);
            telegramChat.setUser(user);

            telegramChatRepository.save(telegramChat);
            return "Ви успішно підписані на сповіщення. Telegram Chat ID: " + chatId;
        } catch (InvalidSubscriptionException e) {
            return "Помилка: Користувач з email " + email
                    + " не зареєстрований як адміністратор або редактор.";
        } catch (AlreadySubscribedException e) {
            return "Помилка: Цей Telegram Chat ID вже підписаний на сповіщення.";
        }
    }

    @Override
    public String getTotalSubscriptions() {
        return telegramChatRepository.findAll().toString();
    }

    @Override
    public String removeSubscriber(Long id) {
        telegramChatRepository.deleteById(id);
        return "You are successfully unsubscribed";
    }

    private User validateAndGetUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidSubscriptionException(
                        email + " is not registered as an admin or editor"));
    }

    private void validateChatNotSubscribed(String chatId) {
        if (telegramChatRepository.existsByChatId(chatId)) {
            throw new AlreadySubscribedException(
                    chatId + " is already subscribed for notifications");
        }
    }
}
