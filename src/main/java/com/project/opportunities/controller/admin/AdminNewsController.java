package com.project.opportunities.controller.admin;

import com.project.opportunities.dto.news.NewsCreateRequestDto;
import com.project.opportunities.dto.news.NewsResponseDto;
import com.project.opportunities.dto.news.NewsUpdateImageDto;
import com.project.opportunities.dto.news.NewsUpdateRequestDto;
import com.project.opportunities.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin News", description = "Endpoints for managing news by admins")
@RestController
@RequestMapping(value = "/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsService newsService;

    @Operation(
            summary = "Create a new news article",
            description = "Allows an admin or ad admin to create "
            + "a new news article."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public NewsResponseDto createNews(@ModelAttribute @Valid NewsCreateRequestDto createRequestDto,
                                      Authentication authentication) {
        return newsService.createNews(createRequestDto, authentication);
    }

    @Operation(
            summary = "Update an existing news article",
            description = "Allows an editor or an admin to update an "
            + "existing news article by its ID."
    )
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    public NewsResponseDto updateNewsContent(@PathVariable Long id,
                                      @RequestBody NewsUpdateRequestDto requestDto) {
        return newsService.updateNewsContent(id, requestDto);
    }

    @Operation(
            summary = "Update cover image for a news item",
            description = "Allows an editor to update the cover image for a "
            + "specific news item by its ID.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('EDITOR')")
    public NewsResponseDto updateNewsCoverImage(@PathVariable Long id,
                                                @ModelAttribute NewsUpdateImageDto requestDto) {
        return newsService.updateNewsImage(id, requestDto);
    }
}
