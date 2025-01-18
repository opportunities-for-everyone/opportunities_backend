package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.donation.response.DonationDto;
import com.project.opportunities.service.core.interfaces.DonateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin Donation Management",
        description = "Endpoints for managing donations by Admins"
)
@RestController
@RequestMapping(value = "/admin/donations")
@RequiredArgsConstructor
public class AdminDonationController {
    private final DonateService donateService;

    @Operation(
            summary = "Get general donations",
            description = "Retrieves a paginated list of general donations",
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved general donations",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    @Parameters({
            @Parameter(
                    name = "pageable",
                    description = "Pagination parameters (page, size, sort)",
                    in = ParameterIn.QUERY
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<DonationDto> getGeneralDonations(
            @PageableDefault @ParameterObject Pageable pageable) {
        return donateService.getGeneralDonations(pageable);
    }
}
