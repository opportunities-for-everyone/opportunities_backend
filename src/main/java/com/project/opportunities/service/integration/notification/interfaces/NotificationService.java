package com.project.opportunities.service.integration.notification.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    void sendAdminSiteNotification(String message);

    @Async
    void sendAdminNotification(String message);

    @Async
    void sendNotificationToSuperAdmins(String message);

    @Async
    void sendNotificationToAdmin(String message);

    @Async
    void sendNotificationToEditor(String message);
}
