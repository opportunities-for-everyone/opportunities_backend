package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.model.Project;
import com.project.opportunities.domain.model.ProjectDonation;

public class ProjectNotificationBuilder extends NotificationBuilder<Project> {
    private ProjectDonation projectDonation;

    private ProjectNotificationBuilder(String action) {
        super(action);
    }

    public static ProjectNotificationBuilder action(String action) {
        return new ProjectNotificationBuilder(action);
    }

    public ProjectNotificationBuilder withDonation(ProjectDonation donation) {
        this.projectDonation = donation;
        this.entity = donation.getProject();
        return this;
    }

    @Override
    protected NotificationBuilder<Project> self() {
        return this;
    }

    @Override
    protected String getEntityInfo() {
        if (projectDonation != null) {
            return """
                    
                    %s:
                    Назва проекту: %s
                    Сума донату: %s
                    Валюта: %s
                    Залишилось зібрати: %s
                    Задонатив: %s
                    Email: %s
                    """.formatted(
                    action,
                    entity.getName(),
                    projectDonation.getAmount().doubleValue(),
                    projectDonation.getCurrency().getCode(),
                    entity.getGoalAmount()
                            .subtract(entity.getCollectedAmount())
                            .toEngineeringString(),
                    projectDonation.getDonorName(),
                    projectDonation.getDonorEmail()
            );
        }

        return """
                
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
                entity.getName(),
                entity.getStatus(),
                entity.getGoalAmount(),
                entity.getCollectedAmount(),
                entity.getDeadline(),
                entity.getDescription(),
                entity.getImage().getUrlImage()
        );
    }
}
