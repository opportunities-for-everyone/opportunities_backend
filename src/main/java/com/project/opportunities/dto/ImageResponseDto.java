package com.project.opportunities.dto;

import com.project.opportunities.model.Image;

public record ImageResponseDto(
        Long id,
        String urlImage,
        Image.ImageType type
) {
}
