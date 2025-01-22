package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.model.Partner;

public class PartnerNotificationBuilder extends NotificationBuilder<Partner> {
    private PartnerNotificationBuilder(String action) {
        super(action);
    }

    public static PartnerNotificationBuilder action(String action) {
        return new PartnerNotificationBuilder(action);
    }

    @Override
    protected NotificationBuilder<Partner> self() {
        return this;
    }

    @Override
    protected String getEntityInfo() {
        return """
                
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
                entity.getPartnerName(),
                entity.getPartnerType().getDescription(),
                entity.getLegalAddress(),
                entity.getIdentificationCode(),
                entity.getSiteUrl(),
                entity.getDirector().getFirstName(),
                entity.getDirector().getLastName(),
                entity.getDirector().getPhoneNumber(),
                entity.getDirector().getEmail(),
                entity.getCooperationGoal()
        );
    }
}
