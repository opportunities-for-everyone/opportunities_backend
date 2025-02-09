package com.project.opportunities.domain.dto.donation.request;

import com.project.opportunities.domain.model.Donation;
import com.project.opportunities.validation.phone.PhoneNumber;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;

public record CreateDonateDto(@NotNull
                              @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
                              BigDecimal amount,
                              @NotNull
                              Donation.Currency currency,
                              @NotBlank
                              @Length(min = 12, max = 13)
                              @PhoneNumber
                              String donorPhoneNumber,
                              String donorName,
                              String donorEmail) {
}
