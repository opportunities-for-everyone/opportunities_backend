package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.email.AllowedEmailCreateDto;
import com.project.opportunities.domain.dto.email.AllowedEmailResponseDto;
import com.project.opportunities.service.core.interfaces.AllowedEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/allowed_emails")
@RequiredArgsConstructor
@Tag(
        name = "Admin Allowed Email Management",
        description = "Endpoints for managing allowed emails by Super Admins"
)
public class AdminAllowedEmailsController {
    private final AllowedEmailService allowedEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Add an allowed email",
            description = """
            Adds an email to the allowed email list for registration
            Requires SUPER_ADMIN, role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Email added to allowed list"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid email or email already exists"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    public void addAllowedEmail(@RequestBody @Valid AllowedEmailCreateDto createDto) {
        allowedEmailService.addAllowedEmail(createDto);
    }

    @GetMapping
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @Operation(
            summary = "Get all allowed emails",
            description = """
            Retrieves a paginated list of all allowed emails
            Requires SUPER_ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of allowed emails retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    public Page<AllowedEmailResponseDto> getAllowedEmails(
            @PageableDefault @ParameterObject Pageable pageable) {
        return allowedEmailService.getAllowedEmails(pageable);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(
            summary = "Delete an allowed email",
            description = """
            Deletes an allowed email from the list by its ID
            Requires SUPER_ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email removed from allowed list"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Email not found"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    public void deleteAllowedEmail(@PathVariable Long id) {
        allowedEmailService.removeAllowedEmail(id);
    }
}
