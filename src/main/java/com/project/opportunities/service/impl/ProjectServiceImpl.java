package com.project.opportunities.service.impl;

import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.CreateDonateDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import com.project.opportunities.dto.donation.ProjectDonationDto;
import com.project.opportunities.dto.project.CreateProjectRequestDto;
import com.project.opportunities.dto.project.ProjectResponseDto;
import com.project.opportunities.dto.project.UpdateProjectStatusDto;
import com.project.opportunities.exception.DonationProcessingException;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.ProjectDonationMapper;
import com.project.opportunities.mapper.ProjectMapper;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.Project;
import com.project.opportunities.model.ProjectDonation;
import com.project.opportunities.repository.ProjectDonationRepository;
import com.project.opportunities.repository.ProjectRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.PaymentGenerationService;
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
    private final ProjectDonationRepository projectDonationRepository;
    private final ProjectDonationMapper projectDonationMapper;
    private final ProjectMapper projectMapper;
    private final ImageService imageService;
    private final PaymentGenerationService paymentGenerationService;

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
    public String acceptDonation(Long projectId, CreateDonateDto requestDto) {
        Project projectById = findProjectById(projectId);
        if (!projectById.getStatus().equals(Project.ProjectStatus.ACTIVE)) {
            throw new DonationProcessingException(
                    "Project with ID: " + projectId + " not active.");
        }
        return paymentGenerationService.getProjectPaymentForm(requestDto, projectId);
    }

    @Override
    public void updateCollectedAmount(Long projectId, BigDecimal amount) {
        Project projectById = findProjectById(projectId);
        projectById.setCollectedAmount(
                projectById.getCollectedAmount().add(amount));
        checkStatus(projectById);
        projectRepository.save(projectById);
    }

    @Override
    public void processDonation(PaymentResponseDto paymentResponseDto,
                                AdditionalPaymentResponseDto additionalPaymentResponseDto) {
        Project projectById = findProjectById(
                Long.valueOf(additionalPaymentResponseDto.donationId()));
        updateCollectedAmount(projectById.getId(), paymentResponseDto.amount());
        ProjectDonation projectDonation = projectDonationMapper.toProjectDonation(
                paymentResponseDto, additionalPaymentResponseDto);
        projectDonation.setProject(projectById);
        projectDonationRepository.save(projectDonation);
    }

    @Override
    public Page<ProjectDonationDto> getProjectDonations(Long id, Pageable pageable) {
        return projectDonationRepository.findAllByProject_Id(id, pageable)
                .map(projectDonationMapper::toProjectDonationDto);
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
