package com.project.opportunities.service.impl;

import com.project.opportunities.model.TelegramChat;
import com.project.opportunities.repository.TelegramChatRepository;
import com.project.opportunities.service.NotificationService;
import com.project.opportunities.service.TelegramBot;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService {
    private final TelegramBot telegramBot;
    private final TelegramChatRepository telegramChatRepository;

    @Override
    @Async
    public void sendAdminNotification(String message) {
        List<TelegramChat> chats = telegramChatRepository.findAll();

        for (TelegramChat chat : chats) {
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chat.getChatId());
                sendMessage.setText(message);

                telegramBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Failed to send message to chat {}", chat.getChatId(), e);
            }
        }
    }
}
