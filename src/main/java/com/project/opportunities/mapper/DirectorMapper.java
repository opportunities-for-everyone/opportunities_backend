package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.dto.director.DirectorDto;
import com.project.opportunities.dto.partner.CreatePartnerRequestDto;
import com.project.opportunities.model.Director;
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
