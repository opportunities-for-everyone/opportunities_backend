package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.partner.request.CreatePartnerRequestDto;
import com.project.opportunities.domain.dto.partner.response.PartnerAllInfoDto;
import com.project.opportunities.domain.dto.partner.response.PartnerGeneralInfoDto;
import com.project.opportunities.domain.model.Partner;
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
