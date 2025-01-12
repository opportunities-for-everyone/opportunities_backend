package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.dto.donation.AdditionalPaymentResponseDto;
import com.project.opportunities.dto.donation.DonationDto;
import com.project.opportunities.dto.donation.PaymentResponseDto;
import com.project.opportunities.model.Donation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface DonationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", source = "mainDto.amount")
    @Mapping(target = "currency", source = "mainDto.currency")
    @Mapping(target = "donationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "donationType", source = "additionalDto.donationType")
    @Mapping(target = "donorName", source = "additionalDto.donorName")
    @Mapping(target = "donorEmail", source = "additionalDto.donorEmail")
    Donation toGeneralDonation(
            PaymentResponseDto mainDto,
            AdditionalPaymentResponseDto additionalDto);

    DonationDto toGeneralDonationDto(Donation donation);
}
