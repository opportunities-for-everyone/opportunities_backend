package com.project.opportunities.domain.dto.documents.response;

import java.time.LocalDateTime;

public record DocumentDto(Long id,
                          String fileName,
                          String description,
                          LocalDateTime uploadedAt) {
}
