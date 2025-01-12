package com.project.opportunities.config;

import com.project.opportunities.exception.TelegramBotRegistrationException;
import com.project.opportunities.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig {
    private static final String EXCEPTION_MESSAGE
            = "Error when registering the Telegram bot, check the token in the env file";

    private final TelegramBot telegramBot;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            log.error(EXCEPTION_MESSAGE, e);
            throw new TelegramBotRegistrationException(
                    EXCEPTION_MESSAGE, e);
        }
    }
}

