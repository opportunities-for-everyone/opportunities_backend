package com.project.opportunities.domain.dto.documents.request;

import com.project.opportunities.domain.model.Document;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UploadDocumentDto(@Length(min = 5, max = 255)
                                @NotBlank(message = "File name is required")
                                String fileName,
                                @NotBlank(message = "Description is required")
                                @Length(min = 5, max = 255)
                                String description,
                                @NotBlank(message = "Category is required")
                                @Length(max = 20)
                                Document.DocumentCategory category) {
}
