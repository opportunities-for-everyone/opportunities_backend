package com.project.opportunities.dto.news;

import com.project.opportunities.validation.image.ValidImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record NewsUpdateImageDto(
        @NotNull
        @ValidImage
        MultipartFile coverImage
) {
}
