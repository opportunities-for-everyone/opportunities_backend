package com.project.opportunities.controller.quest;

import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping(value = "/{id}")
    public ProjectResponseDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping(value = "/all")
    public Page<ProjectResponseDto> getAllProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @GetMapping(value = "/active")
    public Page<ProjectResponseDto> getActiveProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getActiveProjects(pageable);
    }

    @GetMapping(value = "/successful")
    public Page<ProjectResponseDto> getSuccessfulProjects(
            @ParameterObject @PageableDefault Pageable pageable) {
        return projectService.getSuccessfulProjects(pageable);
    }
}
