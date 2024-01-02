/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author drjal
 */
public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

    private static final String LONGITUDE_PATTERN = "^[-+]?([1-9]?\\d{0,2}(\\.\\d{1,8})?|1[0-7]\\d{0,1}(\\.\\d{1,8})?|180(\\.\\d{1,8})?)$";

    @Override
    public void initialize(Longitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext context) {
        if (longitude == null) {
            return false; // Let @NotBlank or @NotNull handle empty values
        }

        if (longitude < -180 || longitude > 180) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Longitude must be between -180 and 180").addConstraintViolation();
            return false;
        }
        String longitudeString = String.valueOf(longitude);
        if (!isValidFormat(longitudeString)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid longitude format").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isValidFormat(String longitude) {
        Pattern pattern = Pattern.compile(LONGITUDE_PATTERN);
        Matcher matcher = pattern.matcher(longitude);
        return matcher.matches();
    }

}
