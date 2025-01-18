package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.news.request.NewsCreateRequestDto;
import com.project.opportunities.domain.dto.news.request.NewsUpdateRequestDto;
import com.project.opportunities.domain.dto.news.response.NewsResponseDto;
import com.project.opportunities.domain.dto.news.response.NewsUpdateImageDto;
import com.project.opportunities.service.core.interfaces.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin News Management",
        description = "Endpoints for managing news by admins"
)
@RestController
@RequestMapping(value = "/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsService newsService;

    @Operation(
            summary = "Create news article",
            description = """
                    Creates a new news article with title, content, and cover image.
                    Requires ADMIN role.
                    """,
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "News article created successfully",
                    content = @Content(schema = @Schema(implementation = NewsResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Admin rights required"
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public NewsResponseDto createNews(
            @Parameter(description = "News article details with cover image")
            @ModelAttribute @Valid NewsCreateRequestDto createRequestDto,
            Authentication authentication) {
        return newsService.createNews(createRequestDto, authentication);
    }

    @Operation(
            summary = "Update news content",
            description = """
                    Updates title and content of an existing news article.
                    Requires EDITOR or ADMIN role.
                    """,
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "News article updated successfully",
                    content = @Content(schema = @Schema(implementation = NewsResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Editor rights required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "News article not found"
            )
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    public NewsResponseDto updateNewsContent(
            @Parameter(description = "ID of the news article to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated news content", required = true)
            @RequestBody NewsUpdateRequestDto requestDto) {
        return newsService.updateNewsContent(id, requestDto);
    }

    @Operation(
            summary = "Update news cover image",
            description = """
                    Updates the cover image of an existing news article.
                    Requires EDITOR or ADMIN role.
                    """,
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cover image updated successfully",
                    content = @Content(schema = @Schema(implementation = NewsResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid image format"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Editor rights required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "News article not found"
            )
    })
    @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('EDITOR')")
    public NewsResponseDto updateNewsCoverImage(
            @Parameter(description = "ID of the news article", required = true)
            @PathVariable Long id,
            @Parameter(description = "New cover image", required = true)
            @ModelAttribute NewsUpdateImageDto requestDto) {
        return newsService.updateNewsImage(id, requestDto);
    }

    @Operation(
            summary = "Delete news article",
            description = """
                Deletes an existing news article by its ID.
                Requires EDITOR or ADMIN role.
                """,
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "News article deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Editor rights required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "News article not found"
            )
    })
    @PreAuthorize("hasRole('EDITOR')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(
            @Parameter(description = "ID of the news article to delete", required = true)
            @PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
