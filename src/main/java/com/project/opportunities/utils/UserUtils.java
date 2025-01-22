package com.project.opportunities.utils;

import com.project.opportunities.domain.dto.user.notification.AdminPanelUserNotificationDto;
import com.project.opportunities.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static AdminPanelUserNotificationDto getCurrentAdminPanelUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return new AdminPanelUserNotificationDto(
                user.getFirstName(),
                user.getLastName(),
                user.getRoles()
        );
    }
}
