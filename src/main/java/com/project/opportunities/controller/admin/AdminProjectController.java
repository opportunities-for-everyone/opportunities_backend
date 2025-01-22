package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.donation.response.ProjectDonationDto;
import com.project.opportunities.domain.dto.project.request.CreateProjectRequestDto;
import com.project.opportunities.domain.dto.project.request.UpdateProjectStatusDto;
import com.project.opportunities.domain.dto.project.response.ProjectResponseDto;
import com.project.opportunities.service.core.interfaces.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
        name = "Admin Project Management",
        description = "Endpoints for managing projects by Admins"
)
@RestController
@RequestMapping(value = "/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {
    private final ProjectService projectService;

    @Operation(
            summary = "Create a new project",
            description = """
            Creates a new project based on the provided request
            Requires SUPER_ADMIN, ADMIN or EDITOR role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Project created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    public ProjectResponseDto createProject(
            @ModelAttribute @Valid CreateProjectRequestDto createProjectRequestDto) {
        return projectService.createProject(createProjectRequestDto);
    }

    @Operation(
            summary = "Update project status",
            description = """
            Updates the status of the project with the given ID
            Requires SUPER_ADMIN, ADMIN or EDITOR role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project status updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'EDITOR')")
    @PatchMapping(value = "/{id}/status")
    public ProjectResponseDto updateProjectStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProjectStatusDto statusDto) {
        return projectService.updateProjectStatus(id, statusDto);
    }

    @Operation(
            summary = "Get project donations",
            description = """
            Retrieves a paginated list of donations for a specific project
            Requires SUPER_ADMIN or ADMIN role.
            """,
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved project donations",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found"
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
            ),
            @Parameter(
                    name = "id",
                    description = "Project ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(value = "/{id}/donations")
    public Page<ProjectDonationDto> getProjectDonations(
            @ParameterObject @PageableDefault Pageable pageable,
            @PathVariable Long id) {
        return projectService.getProjectDonations(id, pageable);
    }
}
