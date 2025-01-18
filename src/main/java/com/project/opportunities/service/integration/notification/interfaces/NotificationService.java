package com.project.opportunities.service.integration.notification.interfaces;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    void sendAdminSiteNotification(String message);

    void sendAdminPanelNotification(AdminPanelUserNotificationDto performer, String message);

    @Async
    void sendAdminNotification(String message);
}
