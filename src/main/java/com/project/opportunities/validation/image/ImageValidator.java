package com.project.opportunities.validation.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Handle @NotNull validation
        }
        String mimeType = file.getContentType();
        return mimeType != null && mimeType.startsWith("image/");
    }
}
