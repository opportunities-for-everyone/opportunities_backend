package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.email.AllowedEmailResponseDto;
import com.project.opportunities.domain.model.AllowedEmail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AllowedEmailMapper {
    @Mapping(target = "roleName", source = "role.roleName")
    AllowedEmailResponseDto toDto(AllowedEmail allowedEmail);
}
