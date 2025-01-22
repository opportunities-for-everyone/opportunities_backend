package com.project.opportunities.service.core.impl;

import com.project.opportunities.domain.dto.email.AllowedEmailCreateDto;
import com.project.opportunities.domain.dto.email.AllowedEmailResponseDto;
import com.project.opportunities.domain.model.AllowedEmail;
import com.project.opportunities.exception.AllowedEmailCreationException;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.exception.RegistrationException;
import com.project.opportunities.mapper.AllowedEmailMapper;
import com.project.opportunities.repository.AllowedEmailRepository;
import com.project.opportunities.repository.UserRepository;
import com.project.opportunities.service.core.interfaces.AllowedEmailService;
import com.project.opportunities.service.core.interfaces.RoleService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllowedEmailServiceImpl implements AllowedEmailService {
    private final AllowedEmailRepository allowedEmailRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AllowedEmailMapper allowedEmailMapper;

    @Override
    @Transactional
    public void addAllowedEmail(AllowedEmailCreateDto createDto) {
        if (allowedEmailRepository.existsByEmail(createDto.email())) {
            log.warn("Email {} already in allowed emails list!", createDto.email());
            throw new AllowedEmailCreationException(
                    "Email: %s - already in allowed emails list!".formatted(createDto.email())
            );
        }
        if (userRepository.existsByEmail(createDto.email())) {
            log.warn("Admin with email {} already registered", createDto.email());
            throw new AllowedEmailCreationException(
                    "Admin with email: %s - already registered!".formatted(createDto.email())
            );
        }
        AllowedEmail allowedEmail = new AllowedEmail();
        allowedEmail.setEmail(createDto.email());
        allowedEmail.setCreatedAt(LocalDateTime.now());
        allowedEmail.setRole(
                roleService.findRoleByName(createDto.roleName())
        );
        log.info("Added allowedEmail: {} with role: {}",createDto.email(), createDto.roleName());
        allowedEmailRepository.save(allowedEmail);
    }

    @Override
    public Page<AllowedEmailResponseDto> getAllowedEmails(Pageable pageable) {
        return allowedEmailRepository.findAll(pageable).map(allowedEmailMapper::toDto);
    }

    @Override
    public void removeAllowedEmail(Long id) {
        AllowedEmail allowedEmail = allowedEmailRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(
                        "Allowed email with id: %s - not found".formatted(id)
                )
        );
        allowedEmailRepository.deleteById(id);
        log.info("Removed allowed email: {}",allowedEmail.getEmail());
    }

    @Override
    public AllowedEmail getAllowedEmail(String email) {
        return allowedEmailRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Email: {} - is not allowed for registration!", email);
            return new RegistrationException(
                    "Your email: %s - is not allowed for registration!".formatted(email)
            );
        });
    }
}
