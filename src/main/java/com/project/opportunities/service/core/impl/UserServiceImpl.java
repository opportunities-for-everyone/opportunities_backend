package com.project.opportunities.service.core.impl;

import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.model.Image;
import com.project.opportunities.domain.model.Role;
import com.project.opportunities.domain.model.User;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.exception.RegistrationException;
import com.project.opportunities.mapper.UserMapper;
import com.project.opportunities.repository.RoleRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.core.interfaces.ImageService;
import com.project.opportunities.service.core.interfaces.UserService;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto) {
        log.info("Starting registration for user with email: {}", registrationRequestDto.email());

        if (userRepository.existsByEmail(registrationRequestDto.email())) {
            log.warn("User with email {} already registered", registrationRequestDto.email());
            throw new RegistrationException("User with email ["
                    + registrationRequestDto.email() + "] already registered!");
        }

        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));

        log.debug("Assigning roles to user with email: {}", registrationRequestDto.email());
        user.setRoles(Set.of(roleRepository.findByRoleName(Role.RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> {
                            log.error("Role ADMIN not found!");
                            return new EntityNotFoundException("Role ADMIN not found!");
                        }),
                roleRepository.findByRoleName(Role.RoleName.ROLE_EDITOR)
                        .orElseThrow(() -> {
                            log.error("Role EDITOR not found!");
                            return new EntityNotFoundException("Role EDITOR not found!");
                        })));

        log.debug("Uploading avatar for user with email: {}", registrationRequestDto.email());
        Image image = imageService.uploadImage(
                registrationRequestDto.avatar(),
                Image.ImageType.TEAM_MEMBER_AVATAR_IMAGE);
        user.setAvatar(image);

        log.debug("Saving user with email: {}", registrationRequestDto.email());
        userRepository.save(user);

        log.info("Successfully registered user with email: {}", registrationRequestDto.email());

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
