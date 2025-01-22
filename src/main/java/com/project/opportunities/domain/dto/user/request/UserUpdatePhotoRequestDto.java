package com.project.opportunities.domain.dto.user.request;

import com.project.opportunities.validation.image.ValidImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdatePhotoRequestDto(@ValidImage @NotNull MultipartFile avatar) {
}
