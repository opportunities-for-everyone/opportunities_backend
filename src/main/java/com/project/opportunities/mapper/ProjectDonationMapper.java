package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.donation.response.AdditionalPaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.PaymentResponseDto;
import com.project.opportunities.domain.dto.donation.response.ProjectDonationDto;
import com.project.opportunities.domain.model.ProjectDonation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {DonationMapper.class, ProjectMapper.class})
public interface ProjectDonationMapper {
    @Mapping(target = "donationDate", expression = "java(java.time.LocalDateTime.now())")
    ProjectDonation toProjectDonation(PaymentResponseDto main,
                                      AdditionalPaymentResponseDto additional);

    ProjectDonationDto toProjectDonationDto(ProjectDonation projectDonation);
}
