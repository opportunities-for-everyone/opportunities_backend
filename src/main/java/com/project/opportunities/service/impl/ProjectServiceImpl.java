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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("Starting creation of new project: {}", createProjectRequestDto.name());
        Project project = projectMapper.toModelFromCreateDto(createProjectRequestDto);

        log.debug("Uploading project image");
        Image projectImage = imageService.uploadImage(
                createProjectRequestDto.image(),
                Image.ImageType.PROJECT_IMAGE);
        project.setImage(projectImage);
        Project savedProject = projectRepository.save(project);
        log.info("Successfully created project with ID: {}", savedProject.getId());
        return projectMapper.toDto(savedProject);
    }

    @Override
    public ProjectResponseDto updateProjectStatus(Long id, UpdateProjectStatusDto statusDto) {
        log.info("Updating status for project ID: {}", id);
        Project project = findProjectById(id);
        log.debug("Current status of project ID: {} is {}", id, project.getStatus());
        project.setStatus(statusDto.status());
        log.debug("New status for project ID: {} is {}", id, statusDto.status());
        projectRepository.save(project);//todo:check better kind of solution
        log.info("Successfully updated status for project ID: {}", id);
        return projectMapper.toDto(project);
    }

    @Override
    public String acceptDonation(Long projectId, CreateDonateDto requestDto) {
        log.info("Starting donation acceptance for project ID: {}", projectId);
        Project projectById = findProjectById(projectId);
        if (!projectById.getStatus().equals(Project.ProjectStatus.ACTIVE)) {
            log.error("Project with ID: {} is not active. Donation cannot be processed.",
                    projectId);
            throw new DonationProcessingException(
                    "Project with ID: " + projectId + " is not active.");
        }
        log.info("Project with ID: {} is active. Generating payment form.", projectId);
        String paymentForm = paymentGenerationService.getProjectPaymentForm(requestDto, projectId);
        log.info("Payment form successfully generated for project ID: {}", projectId);
        return paymentForm;
    }

    @Override
    @Transactional
    public void updateCollectedAmount(Long projectId, BigDecimal amount) {
        log.info("Updating collected amount for project ID: {}, amount to add: {}",
                projectId, amount);
        Project projectById = findProjectById(projectId);
        BigDecimal newAmount = projectById.getCollectedAmount().add(amount);

        log.debug("New total amount will be: {}", newAmount);
        projectById.setCollectedAmount(newAmount);

        checkStatus(projectById);
        projectRepository.save(projectById);

        log.info("Successfully updated collected amount for project ID: {}", projectId);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findProjectById(id).orElseThrow(() -> {
            log.error("Project with id {} not found", id);
            return new EntityNotFoundException("Project with id " + id + " not found");
        });
    }

    @Override
    @Transactional
    public void processDonation(PaymentResponseDto paymentResponseDto,
                                AdditionalPaymentResponseDto additionalPaymentResponseDto) {
        log.info("Starting donation processing. Donation ID: {}, Amount: {}",
                additionalPaymentResponseDto.donationId(), paymentResponseDto.amount());

        Project projectById = findProjectById(
                Long.valueOf(additionalPaymentResponseDto.donationId()));
        log.debug("Project found: ID: {}, Name: {}, Collected: {}, Goal: {}",
                projectById.getId(), projectById.getName(),
                projectById.getCollectedAmount(), projectById.getGoalAmount());

        log.debug("Updating collected amount for project ID: {}", projectById.getId());
        updateCollectedAmount(projectById.getId(), paymentResponseDto.amount());

        ProjectDonation projectDonation = projectDonationMapper.toProjectDonation(
                paymentResponseDto, additionalPaymentResponseDto);
        projectDonation.setProject(projectById);

        log.info("Saving donation information for project ID: {}", projectById.getId());
        projectDonationRepository.save(projectDonation);

        log.info("Donation processed successfully. Donation ID: {}, Project ID: {}, Amount: {}",
                additionalPaymentResponseDto.donationId(),
                projectById.getId(),
                paymentResponseDto.amount());
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
}
