package com.project.opportunities.utils;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import com.project.opportunities.domain.model.Volunteer;

public class VolunteerNotificationBuilder {
    private final String action;
    private AdminPanelUserNotificationDto performer;
    private Volunteer volunteer;

    private VolunteerNotificationBuilder(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action must not be null or blank.");
        }
        this.action = action;
    }

    public static VolunteerNotificationBuilder action(String action) {
        return new VolunteerNotificationBuilder(action);
    }

    public VolunteerNotificationBuilder performer(AdminPanelUserNotificationDto performer) {
        this.performer = performer;
        return this;
    }

    public VolunteerNotificationBuilder withVolunteer(Volunteer volunteer) {
        if (volunteer == null) {
            throw new IllegalArgumentException("Volunteer must not be null.");
        }
        this.volunteer = volunteer;
        return this;
    }

    public String build() {
        validateVolunteer();

        String message = """
                
                %s:
                Ім'я: %s
                Прізвище: %s
                По батькові: %s
                Номер телефону: %s
                Електронна пошта: %s
                Статус: %s
                """.formatted(
                    action,
                    volunteer.getFirstName(),
                    volunteer.getLastName(),
                    volunteer.getMiddleName(),
                    volunteer.getPhoneNumber(),
                    volunteer.getEmail(),
                    volunteer.getStatus()
        );

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

        return performerInfo + message;
    }

    private void validateVolunteer() {
        if (volunteer == null) {
            throw new IllegalStateException(
                    "Volunteer details must be provided to build the notification.");
        }
    }
}
