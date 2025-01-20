package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.model.Volunteer;

public class VolunteerNotificationBuilder extends NotificationBuilder<Volunteer> {

    private VolunteerNotificationBuilder(String action) {
        super(action);
    }

    public static VolunteerNotificationBuilder action(String action) {
        return new VolunteerNotificationBuilder(action);
    }

    @Override
    protected NotificationBuilder<Volunteer> self() {
        return this;
    }

    @Override
    protected String getEntityInfo() {
        return """
                
                %s:
                Ім'я: %s
                Прізвище: %s
                По батькові: %s
                Номер телефону: %s
                Електронна пошта: %s
                Статус: %s
                """.formatted(
                action,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getMiddleName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getStatus()
        );
    }
}
