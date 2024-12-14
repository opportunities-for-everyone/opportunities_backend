package com.project.opportunities.controller;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public NewsResponseDto createNews(@RequestBody @Valid NewsCreateRequestDto createRequestDto,
                                      Authentication authentication) {
        return newsService.createNews(createRequestDto, authentication);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<NewsResponseDto> getAllNews(@ParameterObject @PageableDefault Pageable pageable) {
        return newsService.getAllNews(pageable);
    }
}
