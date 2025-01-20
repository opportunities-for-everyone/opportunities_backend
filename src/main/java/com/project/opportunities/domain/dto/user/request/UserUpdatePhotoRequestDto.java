package com.project.opportunities.domain.dto.user.request;

import com.project.opportunities.validation.image.ValidImage;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdatePhotoRequestDto(@ValidImage MultipartFile avatar) {
}
