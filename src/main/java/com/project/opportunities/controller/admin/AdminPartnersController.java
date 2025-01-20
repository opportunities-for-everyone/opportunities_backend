package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.partner.request.CreatePartnerRequestDto;
import com.project.opportunities.domain.dto.partner.request.UpdatePartnerStatusRequestDto;
import com.project.opportunities.domain.dto.partner.response.PartnerAllInfoDto;
import com.project.opportunities.service.core.interfaces.PartnerService;
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
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin Partner Management",
        description = "Endpoints for managing partners by admins"
)
@RestController
@RequestMapping(value = "/admin/partners")
@RequiredArgsConstructor
public class AdminPartnersController {
    private final PartnerService partnerService;

    @Operation(
            summary = "Get pending partner applications",
            description = """
            Fetches a paginated list of partners whose applications are pending approval
            Requires SUPER_ADMIN or ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved pending partners"
                    )
            }
    )
    @GetMapping()
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public Page<PartnerAllInfoDto> getPendingPartners(
            @ParameterObject @PageableDefault Pageable pageable) {
        return partnerService.getPendingPartners(pageable);
    }

    @Operation(
            summary = "Update partner status",
            description = """
            Updates the status of a specific partner by ID
            Requires SUPER_ADMIN or ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Partner status updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Partner not found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public PartnerAllInfoDto updatePartnerStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePartnerStatusRequestDto requestDto) {
        return partnerService.updatePartnerStatus(id, requestDto);
    }

    @Operation(
            summary = "Add a new partner",
            description = """
            Creates and adds a new partner to the system
            Requires SUPER_ADMIN or ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Partner added successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public PartnerAllInfoDto addPartner(
            @ModelAttribute @Valid CreatePartnerRequestDto requestDto) {
        return partnerService.addPartner(requestDto);
    }

    @Operation(
            summary = "Get all partners",
            description = """
            Fetches a paginated list of all partners
            Requires SUPER_ADMIN, ADMIN or EDiTOR role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all partners"
                    )
            }
    )
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    public Page<PartnerAllInfoDto> getAllPartners(
            @ParameterObject @PageableDefault Pageable pageable) {
        return partnerService.getAllInfoPartners(pageable);
    }
}
