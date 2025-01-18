package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    @Mapping(target = "photoUrl", source = "avatar.urlImage")
    UserResponseDto toDto(User user);
}
