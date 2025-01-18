package com.project.opportunities.domain.dto.news.response;

import com.project.opportunities.validation.image.ValidImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record NewsUpdateImageDto(
        @NotNull
        @ValidImage
        MultipartFile coverImage
) {
}
