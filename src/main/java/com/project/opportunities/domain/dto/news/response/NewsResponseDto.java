package com.project.opportunities.domain.dto.news.response;

import com.project.opportunities.domain.dto.user.response.UserResponseDto;
import java.time.LocalDateTime;

public record NewsResponseDto(
        Long id,
        UserResponseDto author,
        String title,
        String content,
        LocalDateTime createTime,
        String coverImageUrl) {
}
