package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import com.project.opportunities.dto.donation.ProjectDonationDto;
import com.project.opportunities.model.ProjectDonation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {DonationMapper.class, ProjectMapper.class})
public interface ProjectDonationMapper {
    @Mapping(target = "donationDate", expression = "java(java.time.LocalDateTime.now())")
    ProjectDonation toProjectDonation(PaymentResponseDto main,
                                      AdditionalPaymentResponseDto additional);

    ProjectDonationDto toProjectDonationDto(ProjectDonation projectDonation);
}
