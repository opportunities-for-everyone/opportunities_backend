package com.project.organization.validation.matcher;

import com.project.organization.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatcherValidator
        implements ConstraintValidator<PasswordMatcher, UserRegistrationRequestDto> {
    private static final String FIELD_NAME = "repeatPassword";
    private static final String ERROR_MESSAGE = "The entered passwords do not match.";

    @Override
    public boolean isValid(UserRegistrationRequestDto registrationRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = registrationRequestDto.password()
                .equals(registrationRequestDto.repeatPassword());

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ERROR_MESSAGE)
                    .addPropertyNode(FIELD_NAME)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
