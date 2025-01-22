package com.project.opportunities.controller.quest;

import com.project.opportunities.domain.dto.volunteer.request.CreateVolunteerRequestDto;
import com.project.opportunities.domain.dto.volunteer.response.VolunteerResponseDto;
import com.project.opportunities.service.core.interfaces.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Volunteer Management",
        description = "Public APIs for managing volunteers"
)
@RestController
@RequestMapping(value = "/public/volunteers")
@RequiredArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;

    @Operation(
            summary = "Submit a new volunteer application",
            description = "Creates a new volunteer application with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Application submitted successfully",
                    content = @Content(schema = @Schema(
                            implementation = VolunteerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public VolunteerResponseDto submitApplication(
            @ModelAttribute @Valid CreateVolunteerRequestDto requestDto) {
        return volunteerService.submitApplication(requestDto);
    }

    @Operation(
            summary = "Get all active volunteers",
            description = "Returns a paginated list of all active volunteers"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved active volunteers"
    )
    @GetMapping(value = "/active")
    public Page<VolunteerResponseDto> getActiveVolunteers(
            @ParameterObject @PageableDefault Pageable pageable) {
        return volunteerService.getActiveVolunteers(pageable);
    }

    @Operation(
            summary = "Get all pending volunteer applications",
            description = "Returns a paginated list of all pending volunteer applications"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved pending applications"
    )
    @GetMapping(value = "/pending")
    public Page<VolunteerResponseDto> getPendingVolunteers(
            @ParameterObject @PageableDefault Pageable pageable) {
        return volunteerService.getPendingVolunteers(pageable);
    }

    @Operation(
            summary = "Get all rejected volunteer applications",
            description = "Returns a paginated list of all rejected volunteer applications"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved rejected applications"
    )
    @GetMapping(value = "/rejected")
    public Page<VolunteerResponseDto> getRejectedVolunteers(
            @ParameterObject @PageableDefault Pageable pageable) {
        return volunteerService.getRejectedVolunteers(pageable);
    }
}
