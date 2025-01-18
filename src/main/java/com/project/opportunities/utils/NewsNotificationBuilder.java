package com.project.opportunities.utils;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import com.project.opportunities.domain.model.News;

public class NewsNotificationBuilder {
    private final String action;
    private AdminPanelUserNotificationDto performer;
    private News news;

    private NewsNotificationBuilder(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action must not be null or blank.");
        }
        this.action = action;
    }

    public static NewsNotificationBuilder action(String action) {
        return new NewsNotificationBuilder(action);
    }

    public NewsNotificationBuilder performer(AdminPanelUserNotificationDto performer) {
        this.performer = performer;
        return this;
    }

    public NewsNotificationBuilder withNews(News news) {
        if (news == null) {
            throw new IllegalArgumentException("News must not be null.");
        }
        this.news = news;
        return this;
    }

    public String build() {
        validateNews();

        String performerInfo = (performer != null)
                ? """
                [ADMIN-PANEL NOTIFICATION]
                Автор: %s %s
                Ролі: %s
                """.formatted(
                performer.firstName(),
                performer.lastName(),
                performer.authorities())
                : """
                [GENERAL NOTIFICATION]
                """;

        String newsInfo = """
                
                %s:
                Заголовок: %s
                Текст: %s
                Фото: %s
                """.formatted(
                    action,
                    news.getTitle(),
                    news.getContent(),
                    news.getCoverImage().getUrlImage()
        );

        return performerInfo + newsInfo;
    }

    private void validateNews() {
        if (news == null) {
            throw new IllegalStateException(
                    "News details must be provided to build the notification.");
        }
    }
}

