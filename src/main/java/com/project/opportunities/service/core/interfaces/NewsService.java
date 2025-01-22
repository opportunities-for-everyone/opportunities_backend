package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.news.request.NewsCreateRequestDto;
import com.project.opportunities.domain.dto.news.request.NewsUpdateRequestDto;
import com.project.opportunities.domain.dto.news.response.NewsResponseDto;
import com.project.opportunities.domain.dto.news.response.NewsUpdateImageDto;
import com.project.opportunities.domain.model.News;
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

    void deleteNews(Long id);
}
