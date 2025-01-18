package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto);

    Long getUserId(Authentication authentication);

    User getUser(Authentication authentication);
}
