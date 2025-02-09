package com.project.opportunities.domain.dto.partner.request;

import com.project.opportunities.domain.model.Partner;
import com.project.opportunities.validation.image.ValidImage;
import com.project.opportunities.validation.phone.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreatePartnerRequestDto(@Length(max = 100)
                                      @NotBlank
                                      @Length(min = 2, max = 50)
                                      String partnerName,
                                      @NotNull
                                      Partner.PartnerType partnerType,
                                      @NotBlank
                                      @Length(min = 2, max = 20)
                                      String directorFirstName,
                                      @NotBlank
                                      @Length(min = 2, max = 20)
                                      String directorLastName,
                                      @NotBlank
                                      @Length(min = 2, max = 20)
                                      String directorMiddleName,
                                      @NotBlank
                                      @PhoneNumber
                                      String directorPhoneNumber,
                                      @NotBlank
                                      @Length(max = 50)
                                      String directorEmail,
                                      @NotBlank
                                      @Length(min = 5, max = 255)
                                      String cooperationGoal,
                                      @NotBlank
                                      @Length(max = 100)
                                      String siteUrl,
                                      @NotBlank
                                      @Length(max = 255)
                                      String legalAddress,
                                      @NotBlank
                                      @Length(max = 8)
                                      String identificationCode,
                                      @NotNull
                                      @ValidImage
                                      MultipartFile logo) {
}
