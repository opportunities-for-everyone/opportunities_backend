package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdatePhotoRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdateRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.dto.user.response.UserResponseGeneralInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto);

    Page<UserResponseGeneralInfoDto> getAllUsers(Pageable pageable);

    void deleteAccount(Long id);

    UserResponseDto updateUserAccountData(
            Authentication authentication,
            UserUpdateRequestDto requestDto);

    UserResponseDto updateUserAccountAvatar(
            Authentication authentication,
            UserUpdatePhotoRequestDto requestDto);
}
