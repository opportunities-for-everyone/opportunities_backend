package com.project.opportunities.domain.dto.user.response;

public record UserResponseGeneralInfoDto(String firstName,
                                          String middleName,
                                          String lastName,
                                          String position,
                                          String avatarUrl) {
}
