package com.project.opportunities.service.integration.telegram.bot;

import com.project.opportunities.service.integration.telegram.interfaces.TelegramService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
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
        } else if (msg.getText().startsWith("/all")) {
            String totalSubscriptions = telegramService.getTotalSubscriptions();
            sendMessage(chatId, totalSubscriptions);
        } else if (msg.getText().startsWith("/remove")) {
            Long l = Long.valueOf(msg.getText().substring(8));
            String totalSubscriptions = telegramService.removeSubscriber(l);
            sendMessage(chatId, totalSubscriptions);
        } else if (msg.getText().startsWith("/logs")) {
            File logFile = new File("logs/application.log");

            if (!logFile.exists()) {
                sendMessage(chatId, "Файл не існує");
            }
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(new InputFile(logFile));
            try {
                execute(sendDocument);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
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
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    private String parseEmail(String message) {
        return message.substring(6);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}
