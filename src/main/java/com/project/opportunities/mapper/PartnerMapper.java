package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.dto.partner.CreatePartnerRequestDto;
import com.project.opportunities.dto.partner.PartnerAllInfoDto;
import com.project.opportunities.dto.partner.PartnerGeneralInfoDto;
import com.project.opportunities.model.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = DirectorMapper.class)
public interface PartnerMapper {
    @Mapping(target = "logoUrl", source = "logo.urlImage")
    PartnerGeneralInfoDto toGeneralInfoDto(Partner partner);

    @Mapping(source = "director", target = "director")
    @Mapping(target = "logoUrl", source = "logo.urlImage")
    PartnerAllInfoDto toAllInfoDto(Partner partner);

    Partner toPartner(CreatePartnerRequestDto requestDto);
}
