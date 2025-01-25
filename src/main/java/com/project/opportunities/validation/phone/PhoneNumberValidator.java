package com.project.opportunities.validation.phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "\\+?\\d{12}";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}
