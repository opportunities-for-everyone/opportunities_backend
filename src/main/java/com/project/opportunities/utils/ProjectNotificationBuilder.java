package com.project.opportunities.utils;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import com.project.opportunities.domain.model.Project;

public class ProjectNotificationBuilder {
    private final String action;
    private AdminPanelUserNotificationDto performer;
    private Project project;

    private ProjectNotificationBuilder(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action must not be null or blank.");
        }
        this.action = action;
    }

    public static ProjectNotificationBuilder action(String action) {
        return new ProjectNotificationBuilder(action);
    }

    public ProjectNotificationBuilder performer(AdminPanelUserNotificationDto performer) {
        this.performer = performer;
        return this;
    }

    public ProjectNotificationBuilder withProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null.");
        }
        this.project = project;
        return this;
    }

    public String build() {
        validateProject();

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

        String projectInfo = """
                
                %s:
                Назва: %s
                Статус: %s
                Цільова сума: %s
                Зібрана сума: %s
                Дата завершення: %s
                Опис: %s
                Посилання на фото: %s
                """.formatted(
                    action,
                    project.getName(),
                    project.getStatus(),
                    project.getGoalAmount(),
                    project.getCollectedAmount(),
                    project.getDeadline(),
                    project.getDescription(),
                    project.getImage().getUrlImage()
        );

        return performerInfo + projectInfo;
    }

    private void validateProject() {
        if (project == null) {
            throw new IllegalStateException(
                    "Project details must be provided to build the notification.");
        }
    }
}
