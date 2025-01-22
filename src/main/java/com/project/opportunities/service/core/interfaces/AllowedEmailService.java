package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.email.AllowedEmailCreateDto;
import com.project.opportunities.domain.dto.email.AllowedEmailResponseDto;
import com.project.opportunities.domain.model.AllowedEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AllowedEmailService {
    void addAllowedEmail(AllowedEmailCreateDto createDto);

    Page<AllowedEmailResponseDto> getAllowedEmails(Pageable pageable);

    void removeAllowedEmail(Long id);

    AllowedEmail getAllowedEmail(String email);
}
