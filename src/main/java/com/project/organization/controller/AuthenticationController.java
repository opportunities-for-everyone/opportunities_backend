package com.project.organization.controller;

import com.project.organization.dto.user.UserDto;
import com.project.organization.dto.user.UserLoginRequestDto;
import com.project.organization.dto.user.UserLoginResponseDto;
import com.project.organization.dto.user.UserRegistrationRequestDto;
import com.project.organization.security.AuthenticationService;
import com.project.organization.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication",
        description = "Endpoints for managing member authentication and registration")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register a new user",
            description = "This endpoint registers a new user by "
            + "providing the registration details."
    )
    @PostMapping("/registration")
    public UserDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @Operation(
            summary = "Login",
            description = "This endpoint allows team member to "
            + "login by providing their credentials."
    )
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
