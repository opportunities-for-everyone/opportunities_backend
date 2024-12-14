package com.project.opportunities.service;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NewsService {
    NewsResponseDto createNews(NewsCreateRequestDto createRequestDto,
                               Authentication authentication);

    Page<NewsResponseDto> getAllNews(Pageable pageable);
}
