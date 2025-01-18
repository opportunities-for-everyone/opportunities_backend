package com.project.opportunities.utils;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import com.project.opportunities.domain.model.Partner;

public class PartnerNotificationBuilder {
    private final String action;
    private AdminPanelUserNotificationDto performer;
    private Partner partner;

    private PartnerNotificationBuilder(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action must not be null or blank.");
        }
        this.action = action;
    }

    public static PartnerNotificationBuilder action(String action) {
        return new PartnerNotificationBuilder(action);
    }

    public PartnerNotificationBuilder performer(AdminPanelUserNotificationDto performer) {
        this.performer = performer;
        return this;
    }

    public PartnerNotificationBuilder withPartner(Partner partner) {
        if (partner == null) {
            throw new IllegalArgumentException("Partner must not be null.");
        }
        this.partner = partner;
        return this;
    }

    public String build() {
        validatePartner();

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

        String partnerInfo = """
                
                %s:
                Назва: %s
                Тип: %s
                Адреса: %s
                Ідентифікаційний код: %s
                Посилання: %s
                Директор: %s %s
                Телефон директора: %s
                Електронна пошта директора: %s
                Мета співпраці: %s
                """.formatted(
                    action,
                    partner.getPartnerName(),
                    partner.getPartnerType().getDescription(),
                    partner.getLegalAddress(),
                    partner.getIdentificationCode(),
                    partner.getSiteUrl(),
                    partner.getDirector().getFirstName(),
                    partner.getDirector().getLastName(),
                    partner.getDirector().getPhoneNumber(),
                    partner.getDirector().getEmail(),
                    partner.getCooperationGoal()
        );

        return performerInfo + partnerInfo;
    }

    private void validatePartner() {
        if (partner == null) {
            throw new IllegalStateException(
                    "Partner details must be provided to build the notification.");
        }
    }
}
