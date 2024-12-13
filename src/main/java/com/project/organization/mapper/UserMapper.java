package com.project.organization.mapper;

import com.project.organization.config.MapperConfig;
import com.project.organization.dto.user.UserDto;
import com.project.organization.dto.user.UserRegistrationRequestDto;
import com.project.organization.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserDto toDto(User user);
}
