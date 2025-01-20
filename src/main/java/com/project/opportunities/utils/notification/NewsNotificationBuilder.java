package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.model.News;

public class NewsNotificationBuilder extends NotificationBuilder<News> {
    private NewsNotificationBuilder(String action) {
        super(action);
    }

    public static NewsNotificationBuilder action(String action) {
        return new NewsNotificationBuilder(action);
    }

    @Override
    protected NotificationBuilder<News> self() {
        return this;
    }

    @Override
    protected String getEntityInfo() {
        return """
                
                %s:
                Заголовок: %s
                Текст: %s
                Фото: %s
                """.formatted(
                action,
                entity.getTitle(),
                entity.getContent(),
                entity.getCoverImage().getUrlImage()
        );
    }
}
