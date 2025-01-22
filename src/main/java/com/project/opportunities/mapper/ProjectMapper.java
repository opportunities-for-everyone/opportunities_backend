package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.project.request.CreateProjectRequestDto;
import com.project.opportunities.domain.dto.project.response.ProjectResponseDto;
import com.project.opportunities.domain.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    Project toModelFromCreateDto(CreateProjectRequestDto createProjectRequestDto);

    @Mapping(target = "imageUrl", source = "image.urlImage")
    ProjectResponseDto toDto(Project savedProject);
}
