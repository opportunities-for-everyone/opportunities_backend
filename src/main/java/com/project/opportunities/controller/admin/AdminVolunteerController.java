package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.volunteer.request.CreateVolunteerRequestDto;
import com.project.opportunities.domain.dto.volunteer.request.UpdateVolunteerStatusRequestDto;
import com.project.opportunities.domain.dto.volunteer.response.VolunteerResponseDto;
import com.project.opportunities.service.core.interfaces.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin Volunteer Management",
        description = "Administrative APIs for managing volunteers"
)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/volunteers")
public class AdminVolunteerController {
    private final VolunteerService volunteerService;

    @Operation(
            summary = "Add a new volunteer",
            description = "Allows administrators to directly add a new volunteer to the system",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Volunteer added successfully",
                    content = @Content(schema = @Schema(
                            implementation = VolunteerResponseDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(responseCode = "403",
                    description = "Access denied - Admin rights required"
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public VolunteerResponseDto addVolunteer(
            @ModelAttribute @Valid CreateVolunteerRequestDto requestDto) {
        return volunteerService.addVolunteer(requestDto);
    }

    @Operation(
            summary = "Update volunteer status",
            description = "Allows administrators to update the status of an existing volunteer",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Volunteer status updated successfully",
                    content = @Content(schema = @Schema(
                            implementation = VolunteerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Admin rights required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Volunteer not found"
            )
    })
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public VolunteerResponseDto updateVolunteerStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVolunteerStatusRequestDto requestDto) {
        return volunteerService.updateVolunteerStatus(id, requestDto);
    }
}
