package com.project.opportunities.service;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.dto.news.NewsUpdateImageDto;
import com.project.opportunities.dto.news.NewsUpdateRequestDto;
import com.project.opportunities.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NewsService {
    NewsResponseDto createNews(NewsCreateRequestDto createRequestDto,
                               Authentication authentication);

    Page<NewsResponseDto> getAllNews(Pageable pageable);

    NewsResponseDto getById(Long id);

    News findNewsById(Long id);

    NewsResponseDto updateNewsContent(Long id, NewsUpdateRequestDto requestDto);

    NewsResponseDto updateNewsImage(Long id, NewsUpdateImageDto requestDto);
}
