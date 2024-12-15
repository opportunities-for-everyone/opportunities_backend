package com.project.opportunities.dto.news;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record NewsUpdateImageDto(
        @NotNull
        MultipartFile coverImage
) {
}
