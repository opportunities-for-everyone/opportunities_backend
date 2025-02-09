package com.project.opportunities.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeadLineValidator.class)
@Documented
public @interface ValidDeadLine {
    String message() default "The deadline must be at least 1 day later than the current date.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
