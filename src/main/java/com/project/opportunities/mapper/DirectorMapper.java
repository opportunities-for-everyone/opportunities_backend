package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.director.response.DirectorDto;
import com.project.opportunities.domain.dto.partner.request.CreatePartnerRequestDto;
import com.project.opportunities.domain.model.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface DirectorMapper {
    @Mapping(source = "directorFirstName", target = "firstName")
    @Mapping(source = "directorLastName", target = "lastName")
    @Mapping(source = "directorMiddleName", target = "middleName")
    @Mapping(source = "directorPhoneNumber", target = "phoneNumber")
    @Mapping(source = "directorEmail", target = "email")
    Director toDirector(CreatePartnerRequestDto requestDto);

    DirectorDto toDirectorDto(Director director);
}
