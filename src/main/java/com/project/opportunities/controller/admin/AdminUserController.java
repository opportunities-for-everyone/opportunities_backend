package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.user.request.UserUpdatePhotoRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdateRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.service.core.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/users")
@RequiredArgsConstructor
@Tag(
        name = "Admin User Management",
        description = "Administrative APIs for managing user accounts, roles, and profile data"
)
public class AdminUserController {
    private final UserService userService;

    @Operation(
            summary = "Delete user account",
            description = """
                    Permanently removes a user account from the system.
                    Only Super Administrators can perform this operation.""",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Account successfully deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User account not found"
                    )
            },
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @Parameter(description = "ID of the user account to delete", required = true)
            @PathVariable Long id) {
        userService.deleteAccount(id);
    }

    @Operation(
            summary = "Update user account data",
            description = """
                    Updates basic information for a user account.
                    Available to Super Admins, Admins, and Editors
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account data updated successfully",
                            content = @Content(schema = @Schema(implementation
                                    = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data provided"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User account not found"
                    )
            },
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping()
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    public UserResponseDto updateUserAccountData(
            @Parameter(description = "Authentication details of the admin user", required = true)
            Authentication authentication,
            @Parameter(description = "Updated user account information", required = true)
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        return userService.updateUserAccountData(authentication, requestDto);
    }

    @Operation(
            summary = "Update user profile image",
            description = """
                    Updates the avatar/profile image for a user account.
                    Available to Super Admins, Admins, and Editors.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile image updated successfully",
                            content = @Content(schema = @Schema(implementation
                                    = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid image format or size"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User account not found"
                    )
            },
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping(value = "/image")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    public UserResponseDto updateUserAccountAvatar(
            @Parameter(description = "Authentication details of the admin user", required = true)
            Authentication authentication,
            @Parameter(description = "New profile image file", required = true)
            @ModelAttribute @Valid UserUpdatePhotoRequestDto requestDto) {
        return userService.updateUserAccountAvatar(authentication, requestDto);
    }
}
