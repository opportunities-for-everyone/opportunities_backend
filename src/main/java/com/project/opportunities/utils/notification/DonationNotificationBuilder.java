package com.project.opportunities.utils.notification;

import com.project.opportunities.domain.model.Donation;

public class DonationNotificationBuilder extends NotificationBuilder<Donation> {

    private DonationNotificationBuilder(String action) {
        super(action);
    }

    public static DonationNotificationBuilder action(String action) {
        return new DonationNotificationBuilder(action);
    }

    @Override
    protected NotificationBuilder<Donation> self() {
        return this;
    }

    @Override
    protected String getEntityInfo() {
        return """
                
                %s:
                Сума донату: %s
                Валюта: %s
                Задонатив: %s
                Email: %s
                """.formatted(
                action,
                entity.getAmount().doubleValue(),
                entity.getCurrency().getCode(),
                entity.getDonorName(),
                entity.getDonorEmail()
        );
    }
}
