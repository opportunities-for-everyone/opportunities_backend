package com.project.opportunities.controller.quest;

import com.project.opportunities.dto.project.DonateProjectRequestDto;
import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Project Management",
        description = "Public APIs for retrieving information about projects"
)
@RestController
@RequestMapping(value = "/public/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @Operation(
            summary = "Get project by ID",
            description = "Retrieve a single project by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved project",
                    content = @Content(schema = @Schema(
                            implementation = ProjectResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found"
            )
    })
    @GetMapping(value = "/{id}")
    public ProjectResponseDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @Operation(
            summary = "Get all projects",
            description = "Retrieve a paginated list of all projects"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all projects"
    )
    @GetMapping(value = "/all")
    public Page<ProjectResponseDto> getAllProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @Operation(
            summary = "Get all active projects",
            description = """
                    Retrieve a paginated list of projects that are currently active
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved active projects"
    )
    @GetMapping(value = "/active")
    public Page<ProjectResponseDto> getActiveProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getActiveProjects(pageable);
    }

    @Operation(
            summary = "Get all successful projects",
            description = """
                    Retrieve a paginated list of projects that have been
                     successfully completed
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved successful projects"
    )
    @GetMapping(value = "/successful")
    public Page<ProjectResponseDto> getSuccessfulProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getSuccessfulProjects(pageable);
    }

    @Operation(
            summary = "Generate a payment link for a project donation",
            description = """
                    This endpoint generates a payment link for a specific
                     project based on the donation amount and currency.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment link generated successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(value = "/{projectId}/donate")
    public String generatePaymentForProject(
            @PathVariable Long projectId,
            @RequestParam Double amount,
            @RequestParam String currency) {
        DonateProjectRequestDto donateProjectRequestDto
                = new DonateProjectRequestDto(BigDecimal.valueOf(amount), currency);
        return projectService.acceptDonation(projectId, donateProjectRequestDto);
    }
}
