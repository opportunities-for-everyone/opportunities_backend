package com.project.opportunities.domain.dto.director.response;

public record DirectorDto(Long id,
                          String firstName,
                          String middleName,
                          String lastName,
                          String email,
                          String phoneNumber) {
}
