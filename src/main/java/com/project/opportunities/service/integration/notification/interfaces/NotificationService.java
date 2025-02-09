package com.project.opportunities.service.integration.notification.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async
    void sendNotificationToSuperAdmins(String message);

    @Async
    void sendNotificationToAdmins(String message);

    @Async
    void sendNotificationToAll(String message);
}
