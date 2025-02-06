package com.project.opportunities.domain.dto.project.request;

import com.project.opportunities.validation.date.ValidDeadLine;
import com.project.opportunities.validation.image.ValidImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "DTO for creating a new project")
public record CreateProjectRequestDto(
        @Schema(description = "Project name",
                example = "Educational Platform for Children",
                minLength = 10,
                maxLength = 100)
        @NotBlank
        @Length(min = 10, max = 100)
        String name,

        @Schema(description = "Detailed project description",
                example = """
                        n innovative educational platform aimed at providing interactive
                         learning experiences for children aged 6-12
                        """)
        @NotBlank
        String description,

        @Schema(description = "Target funding amount",
                example = "50000.00",
                minimum = "0")
        @NotNull
        BigDecimal goalAmount,

        @Schema(description = "Project deadline date",
                example = "2025-12-31",
                type = "string",
                format = "date")
        @NotNull
        @ValidDeadLine
        LocalDate deadline,

        @Schema(description = "Project cover image",
                type = "string",
                format = "binary")
        @NotNull
        @ValidImage
        MultipartFile image
) {
}
