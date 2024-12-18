package com.project.opportunities.service.impl;

import com.project.opportunities.dto.project.CreateProjectRequestDto;
import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.dto.project.UpdateProjectStatusDto;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.ProjectMapper;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.Project;
import com.project.opportunities.repository.ProjectRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ImageService imageService;

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        return projectMapper.toDto(findProjectById(id));
    }

    @Override
    public Page<ProjectResponseDto> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    @Override
    public Page<ProjectResponseDto> getActiveProjects(Pageable pageable) {
        return projectRepository.findAllByStatus_Active(pageable).map(projectMapper::toDto);
    }

    @Override
    public Page<ProjectResponseDto> getSuccessfulProjects(Pageable pageable) {
        return projectRepository.findAllByStatus_SuccessfullyCompleted(pageable)
                .map(projectMapper::toDto);
    }

    @Transactional
    @Override
    public ProjectResponseDto createProject(
            CreateProjectRequestDto createProjectRequestDto) {
        Project project = projectMapper.toModelFromCreateDto(createProjectRequestDto);
        Image projectImage = imageService.uploadImage(
                createProjectRequestDto.image(),
                Image.ImageType.PROJECT_IMAGE);
        project.setImage(projectImage);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public ProjectResponseDto updateProjectStatus(
            Long id, UpdateProjectStatusDto statusDto) {
        Project project = findProjectById(id);
        project.setStatus(statusDto.status());
        Project saved = projectRepository.save(project);
        return projectMapper.toDto(saved);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Project with id " + id + " not found"));
    }
}
