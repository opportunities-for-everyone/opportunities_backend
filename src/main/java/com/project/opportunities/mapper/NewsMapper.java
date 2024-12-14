package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.dto.news.NewsUpdateRequestDto;
import com.project.opportunities.model.News;
import com.project.opportunities.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface NewsMapper {
    @Mapping(target = "author", expression = "java(getCurrentUser())")
    News toModel(NewsCreateRequestDto newsCreateRequestDto);

    News toModel(NewsUpdateRequestDto newsUpdateRequestDto);

    NewsResponseDto toDto(News news);

    default User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal());
    }
}
