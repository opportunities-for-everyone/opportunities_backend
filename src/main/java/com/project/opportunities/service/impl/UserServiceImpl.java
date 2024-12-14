package com.project.opportunities.service.impl;

import com.project.opportunities.dto.user.UserRegistrationRequestDto;
import com.project.opportunities.dto.user.UserResponseDto;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.exception.RegistrationException;
import com.project.opportunities.mapper.UserMapper;
import com.project.opportunities.model.Role;
import com.project.opportunities.model.User;
import com.project.opportunities.repository.RoleRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto) {
        if (userRepository.existsByEmail(registrationRequestDto.email())) {
            throw new RegistrationException("User with email ["
                    + registrationRequestDto.email() + "] already registered!"
            );
        }

        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
        user.setRoles(Set.of(roleRepository.findByRoleName(Role.RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new EntityNotFoundException("Role ADMIN not found!")),
                roleRepository.findByRoleName(Role.RoleName.ROLE_EDITOR)
                        .orElseThrow(() -> new EntityNotFoundException("Role EDITOR not found!"))));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Long getUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Override
    public User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
