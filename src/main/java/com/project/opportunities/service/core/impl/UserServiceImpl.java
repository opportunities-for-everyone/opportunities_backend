package com.project.opportunities.service.core.impl;

import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdatePhotoRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdateRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.dto.user.response.UserResponseGeneralInfoDto;
import com.project.opportunities.domain.model.Image;
import com.project.opportunities.domain.model.Role;
import com.project.opportunities.domain.model.User;
import com.project.opportunities.exception.RegistrationException;
import com.project.opportunities.mapper.UserMapper;
import com.project.opportunities.repository.ImageRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.core.interfaces.ImageService;
import com.project.opportunities.service.core.interfaces.RoleService;
import com.project.opportunities.service.core.interfaces.UserService;
import com.project.opportunities.service.integration.notification.interfaces.NotificationService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AllowedEmailServiceImpl allowedEmailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final NotificationService notificationService;

    @Value("${admin.super.emails}")
    private List<String> superAdminEmails;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto) {
        String email = registrationRequestDto.email();
        log.info("Starting registration for user with email: {}", email);

        validateEmailNotRegistered(email);
        Role userRole = getUserRole(email);

        User user = createUser(registrationRequestDto, userRole);
        uploadUserAvatar(user, registrationRequestDto);

        userRepository.save(user);
        log.info("Successfully registered user with email: {}", email);

        notificationService.sendNotificationToSuperAdmins(
                """
                        [ADMIN_PANEL NOTIFICATION]
                        
                        Зареєстровано новий акаунт в адмін-панелі
                        Прізвище: %s
                        Ім'я: %s
                        По батькові: %s
                        Email: %s
                        """.formatted(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getMiddleName(),
                                user.getEmail()
                )
        );
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResponseGeneralInfoDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toGeneralInfoDto);
    }

    @Override
    public void deleteAccount(Long id) {
        User userById = findUserById(id);
        userRepository.deleteById(id);
        log.info("Successfully deleted account with email: {}", userById.getEmail());

    }

    @Override
    @Transactional
    public UserResponseDto updateUserAccountData(Authentication authentication,
                                                 UserUpdateRequestDto requestDto) {
        log.info("Starting account update for authenticated user.");
        User currentUser = getCurrentUser(authentication);
        log.info("Authenticated user: {}", currentUser.getEmail());

        User user = findUserById(currentUser.getId());
        log.info("Found user to update: {}", user.getEmail());

        checkEmailBeforeUpdate(user, requestDto.email());

        log.info("Updating user account with new data: {}", requestDto);
        userMapper.updateUser(user, requestDto);

        userRepository.save(user);
        log.info("Successfully updated user account for email: {}", user.getEmail());

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUserAccountAvatar(Authentication authentication,
                                                   UserUpdatePhotoRequestDto requestDto) {
        log.info("Starting update avatar for authenticated user.");
        User currentUser = getCurrentUser(authentication);
        log.info("Authenticated user: {}", currentUser.getEmail());

        User user = findUserById(currentUser.getId());
        log.info("Found user to update avatar: {}", user.getEmail());

        Image oldImage = user.getAvatar();
        Image image = imageService.uploadImage(
                requestDto.avatar(),
                Image.ImageType.TEAM_MEMBER_AVATAR_IMAGE);
        user.setAvatar(image);
        userRepository.save(user);
        imageRepository.deleteById(oldImage.getId());
        log.info("Successfully updated user avatar for email: {}", user.getEmail());
        log.info("Successfully deleted old user avatar for email: {}", user.getEmail());
        return userMapper.toDto(user);
    }

    private void validateEmailNotRegistered(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("User with email {} already registered", email);
            throw new RegistrationException("User with email [" + email + "] already registered!");
        }
    }

    private Role getUserRole(String email) {
        if (isSuperAdminEmail(email)) {
            return roleService.findRoleByName(Role.RoleName.ROLE_SUPER_ADMIN);
        }
        return allowedEmailService.getAllowedEmail(email).getRole();
    }

    private boolean isSuperAdminEmail(String email) {
        boolean result = superAdminEmails.contains(email);
        log.info("{} super admin status: {}", email, result);
        return result;
    }

    private User createUser(UserRegistrationRequestDto registrationRequestDto, Role role) {
        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
        user.setRoles(Set.of(roleService.findRoleByName(role.getRoleName())));
        log.info("Created user with email: {} and role: {}",
                user.getEmail(), role.getRoleName());
        return user;
    }

    private void uploadUserAvatar(User user, UserRegistrationRequestDto registrationRequestDto) {
        log.debug("Uploading avatar for user with email: {}", user.getEmail());
        Image image = imageService.uploadImage(
                registrationRequestDto.avatar(),
                Image.ImageType.TEAM_MEMBER_AVATAR_IMAGE);
        user.setAvatar(image);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.info("Account with id: {} not found", id);
            return new IllegalArgumentException("Account with id: %s not found".formatted(id));
        });
    }

    private void checkEmailBeforeUpdate(User user, String emailUpdated) {
        if (userRepository.existsByEmail(emailUpdated) && !user.getEmail().equals(emailUpdated)) {
            throw new RegistrationException("User with email: " + emailUpdated + " already registered!");
        }
    }
    private User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
