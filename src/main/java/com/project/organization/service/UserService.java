package com.project.organization.service;

import com.project.organization.dto.user.UserDto;
import com.project.organization.dto.user.UserRegistrationRequestDto;

public interface UserService {
    UserDto register(UserRegistrationRequestDto registrationRequestDto);
}
