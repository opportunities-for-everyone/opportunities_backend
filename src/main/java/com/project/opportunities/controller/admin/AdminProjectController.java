package com.project.opportunities.controller.admin;

import com.project.opportunities.dto.project.CreateProjectRequestDto;
import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.dto.project.UpdateProjectStatusDto;
import com.project.opportunities.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/projects")
@RequiredArgsConstructor
@Tag(name = "Admin Projects", description = "Endpoints for managing projects by Admins")
public class AdminProjectController {
    private final ProjectService projectService;

    @Operation(
            summary = "Create a new project",
            description = "Creates a new project based on the provided request",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Project created successfully"),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponseDto createProject(
            @ModelAttribute @Valid CreateProjectRequestDto createProjectRequestDto) {
        return projectService.createProject(createProjectRequestDto);
    }

    @Operation(
            summary = "Update project status",
            description = "Updates the status of the project with the given ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Project status updated successfully"),
                    @ApiResponse(responseCode = "404",
                            description = "Project not found"),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid input")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/status")
    public ProjectResponseDto updateProjectStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProjectStatusDto statusDto) {
        return projectService.updateProjectStatus(id, statusDto);
    }
}
