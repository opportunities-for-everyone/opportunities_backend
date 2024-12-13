package com.project.organization.service;

import com.project.organization.dto.user.UserDto;
import com.project.organization.dto.user.UserRegistrationRequestDto;
import com.project.organization.exception.EntityNotFoundException;
import com.project.organization.exception.RegistrationException;
import com.project.organization.mapper.UserMapper;
import com.project.organization.model.Role;
import com.project.organization.model.User;
import com.project.organization.repository.RoleRepository;
import com.project.organization.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequestDto registrationRequestDto) {
        if (userRepository.existsByEmail(registrationRequestDto.email())) {
            throw new RegistrationException("User with email ["
                    + registrationRequestDto.email() + "] already registered!"
            );
        }

        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
        user.setRoles(Set.of(roleRepository.findByRoleName(Role.RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new EntityNotFoundException("Role not found!"))));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
