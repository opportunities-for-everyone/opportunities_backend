package com.project.opportunities.service.integration.notification.impl;

import com.project.opportunities.domain.model.Role;
import com.project.opportunities.domain.model.TelegramChat;
import com.project.opportunities.repository.TelegramChatRepository;
import com.project.opportunities.service.integration.notification.interfaces.NotificationService;
import com.project.opportunities.service.integration.telegram.bot.TelegramBotApi;
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
    private final TelegramBotApi telegramBot;
    private final TelegramChatRepository telegramChatRepository;

    @Override
    @Async
    public void sendAdminSiteNotification(String message) {
        List<TelegramChat> chats = telegramChatRepository.findAll();

        for (TelegramChat chat : chats) {
            sendMessage(chat, message);
        }
    }

    @Async
    @Override
    public void sendAdminNotification(String message) {

        List<TelegramChat> chats = telegramChatRepository.findAll();

        for (TelegramChat chat : chats) {
            sendMessage(chat, message);
        }
    }

    @Async
    @Override
    public void sendNotificationToSuperAdmins(String message) {
        List<TelegramChat> superAdminChats = telegramChatRepository.findChatsByRoles(
                List.of(
                        Role.RoleName.ROLE_SUPER_ADMIN,
                        Role.RoleName.ROLE_ADMIN,
                        Role.RoleName.ROLE_EDITOR
                )
        );
        for (TelegramChat chat : superAdminChats) {
            sendMessage(chat, message);
        }
    }

    @Async
    @Override
    public void sendNotificationToAdmin(String message) {
        List<TelegramChat> adminChats = telegramChatRepository.findChatsByRoles(
                List.of(
                        Role.RoleName.ROLE_SUPER_ADMIN,
                        Role.RoleName.ROLE_ADMIN
                )
        );

        for (TelegramChat chat : adminChats) {
            sendMessage(chat, message);
        }
    }

    @Async
    @Override
    public void sendNotificationToEditor(String message) {
        List<TelegramChat> adminChats = telegramChatRepository.findChatsByRoles(
                List.of(
                        Role.RoleName.ROLE_EDITOR
                )
        );

        for (TelegramChat chat : adminChats) {
            sendMessage(chat, message);
        }
    }

    private void sendMessage(TelegramChat chat, String message) {
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
