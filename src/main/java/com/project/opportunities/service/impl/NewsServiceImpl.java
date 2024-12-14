package com.project.opportunities.service.impl;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.mapper.NewsMapper;
import com.project.opportunities.model.News;
import com.project.opportunities.repository.NewsRepository;
import com.project.opportunities.service.NewsService;
import com.project.opportunities.service.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserService userService;

    @Override
    public NewsResponseDto createNews(NewsCreateRequestDto createRequestDto,
                                      Authentication authentication) {
        News news = newsMapper.toModel(createRequestDto);
        news.setCreateTime(LocalDateTime.now());
        News saved = newsRepository.save(news);
        return newsMapper.toDto(saved);
    }

    @Override
    public Page<NewsResponseDto> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::toDto);
    }
}
