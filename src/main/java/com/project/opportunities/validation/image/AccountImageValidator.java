package com.project.opportunities.validation.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AccountImageValidator implements ConstraintValidator<AccountImage, String> {
    private static final String PATTERN_OF_COVER_IMAGE_URL
            = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return url != null && Pattern.compile(PATTERN_OF_COVER_IMAGE_URL).matcher(url).matches();
    }
}
