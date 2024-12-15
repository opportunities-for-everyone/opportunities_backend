package com.project.opportunities.controller.quest;

import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "Public endpoints for viewing news")
public class NewsController {
    private final NewsService newsService;

    @Operation(
            summary = "Get all news",
            description = "Retrieve a paginated list of all news"
            + " articles available for public viewing."
    )
    @GetMapping
    public Page<NewsResponseDto> getAllNews(@ParameterObject @PageableDefault Pageable pageable) {
        return newsService.getAllNews(pageable);
    }

    @Operation(
            summary = "Get news by ID",
            description = "Retrieve a single news article by its unique ID."
    )
    @GetMapping("/{id}")
    public NewsResponseDto getNewsById(@PathVariable Long id) {
        return newsService.getById(id);
    }
}
