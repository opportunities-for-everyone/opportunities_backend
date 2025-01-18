package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.news.request.NewsCreateRequestDto;
import com.project.opportunities.domain.dto.news.request.NewsUpdateRequestDto;
import com.project.opportunities.domain.dto.news.response.NewsResponseDto;
import com.project.opportunities.domain.model.News;
import com.project.opportunities.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface NewsMapper {
    @Mapping(target = "author", expression = "java(getCurrentUser())")
    News toModel(NewsCreateRequestDto newsCreateRequestDto);

    void updateNewsFromDto(NewsUpdateRequestDto requestDto, @MappingTarget News news);

    @Mapping(target = "coverImageUrl", source = "coverImage.urlImage")
    NewsResponseDto toDto(News news);

    default User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal());
    }
}
