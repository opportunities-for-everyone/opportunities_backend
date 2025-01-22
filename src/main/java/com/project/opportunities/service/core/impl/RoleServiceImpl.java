package com.project.opportunities.service.core.impl;

import com.project.opportunities.domain.model.Role;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.repository.RoleRepository;
import com.project.opportunities.service.core.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> {
            log.error("Role {} not found!", roleName);
            return new EntityNotFoundException(
                        "%s Role not found: ".formatted(roleName)
            );
        });
    }
}
