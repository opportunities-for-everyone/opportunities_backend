package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;
import com.project.opportunities.domain.dto.donation.response.AdditionalPaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.PaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.ProjectDonationDto;
import com.project.opportunities.domain.dto.project.request.CreateProjectRequestDto;
import com.project.opportunities.domain.dto.project.request.UpdateProjectStatusDto;
import com.project.opportunities.domain.dto.project.response.ProjectResponseDto;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto getProjectById(Long id);

    Page<ProjectResponseDto> getAllProjects(Pageable pageable);

    Page<ProjectResponseDto> getActiveProjects(Pageable pageable);

    Page<ProjectResponseDto> getSuccessfulProjects(Pageable pageable);

    ProjectResponseDto createProject(CreateProjectRequestDto createProjectRequestDto);

    ProjectResponseDto updateProjectStatus(Long id,
                                           UpdateProjectStatusDto statusDto);

    String acceptDonation(Long projectId, CreateDonateDto requestDto);

    void updateCollectedAmount(Long projectId, BigDecimal amount);

    void processDonation(PaymentResponseDto paymentResponseDto,
                         AdditionalPaymentResponseDto additionalPaymentResponseDto);

    Page<ProjectDonationDto> getProjectDonations(Long id, Pageable pageable);
}
