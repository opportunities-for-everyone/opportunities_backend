package com.project.opportunities.controller.admin;

import com.project.opportunities.service.integration.telegram.interfaces.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin Telegram Management",
        description = "APIs for administrators to manage Telegram notifications"
)
@RestController
@RequestMapping("/admin/telegram")
@RequiredArgsConstructor
public class AdminTelegramController {
    private final TelegramService telegramService;

    @Operation(
            summary = "Get a link to the Telegram bot for notifications",
            description = """
                    Allows administrators to get a link to the Telegram bot.
                    By subscribing, admins can receive notifications about website operations.
                    Requires SUPER_ADMIN, ADMIN or EDITOR role.
                    """,
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned the Telegram bot link",
                    content = @Content(schema = @Schema(
                            type = "string",
                            example = "https://t.me/YourTelegramBot"))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Access denied - you not an admin or editor"
            )
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    @GetMapping("/notifyMe")
    public String subscribeToNotifications(Authentication authentication) {
        return telegramService.subscribeNotifications(authentication);
    }
}

