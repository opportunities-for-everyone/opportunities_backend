package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.user.request.UserRegistrationRequestDto;
import com.project.opportunities.domain.dto.user.request.UserUpdateRequestDto;
import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import com.project.opportunities.domain.dto.user.response.UserResponseGeneralInfoDto;
import com.project.opportunities.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    @Mapping(target = "photoUrl", source = "avatar.urlImage")
    UserResponseDto toDto(User user);

    @Mapping(target = "avatarUrl", source = "avatar.urlImage")
    UserResponseGeneralInfoDto toGeneralInfoDto(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequestDto requestDto);
}
