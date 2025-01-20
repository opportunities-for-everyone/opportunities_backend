package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.model.Role;

public interface RoleService {
    Role findRoleByName(Role.RoleName roleName);
}
