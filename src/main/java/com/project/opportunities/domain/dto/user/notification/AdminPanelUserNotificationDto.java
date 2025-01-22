package com.project.opportunities.domain.dto.user.notification;

import com.project.opportunities.domain.model.Role;
import java.util.Set;

public record AdminPanelUserNotificationDto(String firstName,
                                            String lastName,
                                            Set<Role> authorities) {
}
