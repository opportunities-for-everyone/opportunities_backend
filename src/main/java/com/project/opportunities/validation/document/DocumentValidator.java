package com.project.opportunities.validation.document;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

public class DocumentValidator implements ConstraintValidator<ValidDocument, MultipartFile> {
    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(ValidDocument constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            addConstraintViolation(context, "File is required");
            return false;
        }

        if (file.getSize() > maxSize) {
            addConstraintViolation(context,
                    String.format("File size must not exceed %d bytes", maxSize));
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(allowedTypes).contains(contentType)) {
            addConstraintViolation(context,
                    String.format("File type must be one of: %s",
                            String.join(", ", allowedTypes)));
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
