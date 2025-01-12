package com.project.opportunities.service.impl;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.dto.news.NewsUpdateImageDto;
import com.project.opportunities.dto.news.NewsUpdateRequestDto;
import com.project.opportunities.exception.EntityNotFoundException;
import com.project.opportunities.mapper.NewsMapper;
import com.project.opportunities.model.Image;
import com.project.opportunities.model.News;
import com.project.opportunities.repository.NewsRepository;
import com.project.opportunities.service.ImageService;
import com.project.opportunities.service.NewsService;
import com.project.opportunities.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final ImageService imageService;
    private final UserService userService;

    @Transactional
    @Override
    public NewsResponseDto createNews(NewsCreateRequestDto createRequestDto,
                                      Authentication authentication) {
        log.info("Creating news with title: {}", createRequestDto.title());
        News news = newsMapper.toModel(createRequestDto);
        news.setCreateTime(LocalDateTime.now());
        Image coverImage = imageService.uploadImage(
                createRequestDto.coverImage(),
                Image.ImageType.NEWS_IMAGE);
        news.setCoverImage(coverImage);
        News saved = newsRepository.save(news);
        log.info("News created successfully. ID: {}, Title: {}",
                saved.getId(), saved.getTitle());
        return newsMapper.toDto(saved);
    }

    @Override
    public Page<NewsResponseDto> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::toDto);
    }

    @Override
    public NewsResponseDto getById(Long id) {
        News newsById = findNewsById(id);
        return newsMapper.toDto(newsById);
    }

    @Override
    public News findNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> {
            log.error("News not found by ID: {}", id);
            return new EntityNotFoundException("Can't find news by id: " + id);
        });
    }

    @Override
    public NewsResponseDto updateNewsContent(Long id, NewsUpdateRequestDto requestDto) {
        log.info("Updating news content. ID: {}", id);
        News newsById = findNewsById(id);
        newsMapper.updateNewsFromDto(requestDto, newsById);
        News saved = newsRepository.save(newsById);
        log.info("News content updated successfully. ID: {}, Title: {}",
                saved.getId(), saved.getTitle());
        return newsMapper.toDto(saved);
    }

    @Override
    public NewsResponseDto updateNewsImage(Long id, NewsUpdateImageDto requestDto) {
        log.info("Updating news image. ID: {}", id);
        News news = findNewsById(id);
        Image updatedImage = imageService.uploadImage(
                requestDto.coverImage(),
                Image.ImageType.NEWS_IMAGE);
        news.setCoverImage(updatedImage);
        newsRepository.save(news);
        log.info("News image updated successfully. ID: {}, New Image URL: {}",
                news.getId(), updatedImage.getUrlImage());
        return newsMapper.toDto(news);
    }

    @Override
    public void deleteNews(Long id) {
        log.info("Attempting to delete news. ID: {}", id);
        findNewsById(id);
        newsRepository.deleteById(id);
        log.info("News deleted successfully. ID: {}", id);
    }
}
