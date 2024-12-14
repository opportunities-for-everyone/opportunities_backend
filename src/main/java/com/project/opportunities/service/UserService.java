package com.project.opportunities.service;

import com.project.opportunities.dto.user.UserRegistrationRequestDto;
import com.project.opportunities.dto.user.UserResponseDto;
import com.project.opportunities.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto);

    Long getUserId(Authentication authentication);

    User getUser(Authentication authentication);
}
