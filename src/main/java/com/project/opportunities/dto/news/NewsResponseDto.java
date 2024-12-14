package com.project.opportunities.dto.news;

import com.project.opportunities.dto.user.UserResponseDto;
import java.time.LocalDateTime;

public record NewsResponseDto(
        Long id,
        UserResponseDto author,
        String title,
        String content,
        LocalDateTime createTime) {
}
