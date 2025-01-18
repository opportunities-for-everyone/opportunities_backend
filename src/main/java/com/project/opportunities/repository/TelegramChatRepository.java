package com.project.opportunities.repository;

import com.project.opportunities.domain.model.TelegramChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {
    boolean existsByChatId(String chatId);
}
