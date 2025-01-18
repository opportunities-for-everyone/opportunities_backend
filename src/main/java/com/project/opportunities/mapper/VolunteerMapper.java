package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.volunteer.request.CreateVolunteerRequestDto;
import com.project.opportunities.domain.dto.volunteer.response.VolunteerResponseDto;
import com.project.opportunities.domain.model.Volunteer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface VolunteerMapper {
    Volunteer toModel(CreateVolunteerRequestDto requestDto);

    @Mapping(target = "avatarUrl", source = "avatar.urlImage")
    VolunteerResponseDto toDto(Volunteer volunteer);
}
