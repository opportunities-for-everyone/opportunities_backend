package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;

public abstract class NotificationBuilder<T> {
    protected final String action;
    protected AdminPanelUserNotificationDto performer;
    protected T entity;

    protected NotificationBuilder(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action must not be null or blank.");
        }
        this.action = action;
    }

    protected abstract NotificationBuilder<T> self();

    public NotificationBuilder<T> performer(AdminPanelUserNotificationDto performer) {
        this.performer = performer;
        return self();
    }

    public NotificationBuilder<T> withEntity(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        this.entity = entity;
        return self();
    }

    public String build() {
        validateEntity();
        return getPerformerInfo() + getEntityInfo();
    }

    protected String getPerformerInfo() {
        return (performer != null)
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
    }

    protected abstract String getEntityInfo();

    protected void validateEntity() {
        if (entity == null) {
            throw new IllegalStateException(
                    "Entity details must be provided to build the notification.");
        }
    }
}
