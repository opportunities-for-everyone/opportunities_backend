package com.project.opportunities.service.integration.telegram.bot;

import com.project.opportunities.service.integration.telegram.interfaces.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotApi extends TelegramLongPollingBot {
    private static final String GREETING_MESSAGE = """
            Hello. Welcome to the bot that sends notifications about the work of our site.
            
            To start receiving news, enter the command /join [your_email]
            Example: /join test.mail@gmail.com
            
            Note:please make sure that you enter the email address
            that your admin account is registered to on the site;
            """;
    private final TelegramService telegramService;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var chatId = msg.getChatId().toString();
        log.info("Received message from user: {} (id: {}), chatId: {}, text: {}",
                user.getFirstName() + " " + user.getLastName(),
                user.getId(),
                chatId,
                msg.getText());
        if (msg.getText().startsWith("/join ")) {
            String email = parseEmail(msg.getText());
            String result = telegramService.subscribeForNotifications(chatId, email);
            sendMessage(chatId, result);
            log.info("Subscription result for email {}: {}", email, result);
        } else {
            sendMessage(chatId, GREETING_MESSAGE);
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String parseEmail(String message) {
        return message.substring(6);
    }
}
