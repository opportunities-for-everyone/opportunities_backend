package com.project.opportunities.domain.dto.donation.request;

import com.project.opportunities.domain.model.Donation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;

public record CreateDonateDto(@NotNull
                                BigDecimal amount,
                              @NotNull
                                Donation.Currency currency,
                              @NotBlank
                                @Length(min = 12, max = 13)
                                String donorPhoneNumber,//todo:add phone number validation
                              String donorName,
                              String donorEmail) {
}
