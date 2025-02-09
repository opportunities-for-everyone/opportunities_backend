package com.project.opportunities.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DeadLineValidator implements ConstraintValidator<ValidDeadLine, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate,
                           ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.now().plusDays(1));
    }
}
