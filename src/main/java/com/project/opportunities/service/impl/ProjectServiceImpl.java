package com.project.opportunities.service.impl;

import com.project.opportunities.dto.project.CreateProjectRequestDto;
import com.project.opportunities.dto.project.DonateProjectRequestDto;
import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.dto.project.UpdateProjectStatusDto;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.ProjectMapper;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.Project;
import com.project.opportunities.repository.ProjectRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.DonateGenerationService;
import com.project.opportunities.service.ProjectService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
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
    private final DonateGenerationService donateGenerationService;

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

    @Override
    public String acceptDonation(Long projectId, DonateProjectRequestDto requestDto) {
        Project projectById = findProjectById(projectId);
        if (!projectById.getStatus().equals(Project.ProjectStatus.ACTIVE)) {
            throw new DonationProcessingException(
                    "Project with ID: " + projectId + " not active.");
        }
        String donationId = "projectID_" + projectId + "_" + System.currentTimeMillis();
        return donateGenerationService.generatePaymentForm(
                requestDto.amount().doubleValue(),
                requestDto.currency(),
                "Благодійний внесок на проект ID:%s".formatted(projectId),
                donationId
        );
    }

    @Override
    public void updateCollectedAmount(Long projectId, BigDecimal amount) {
        Project projectById = findProjectById(projectId);
        projectById.setCollectedAmount(
                projectById.getCollectedAmount().add(amount));
        checkStatus(projectById);
        projectRepository.save(projectById);
    }

    private void checkStatus(Project project) {
        if (project.getCollectedAmount().compareTo(project.getGoalAmount()) >= 0) {
            project.setStatus(Project.ProjectStatus.SUCCESSFULLY_COMPLETED);
        }
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Project with id " + id + " not found"));
    }
}
