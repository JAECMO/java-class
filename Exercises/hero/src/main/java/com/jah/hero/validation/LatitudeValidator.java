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
public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

    private static final String LATITUDE_PATTERN = "^[-+]?([1-8]?\\d(\\.\\d{1,8})?|90(\\.0{1,8})?)$";

    @Override
    public void initialize(Latitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext context) {
        if (latitude == null) {
            return false; // Let @NotBlank or @NotNull handle empty values
        }

        if (latitude < -90 || latitude > 90) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Latitude must be between -90 and 90").addConstraintViolation();
            return false;
        }
        String latitudeString = String.valueOf(latitude);
        if (!isValidFormat(latitudeString)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid latitude format").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isValidFormat(String latitude) {
        Pattern pattern = Pattern.compile(LATITUDE_PATTERN);
        Matcher matcher = pattern.matcher(latitude);
        return matcher.matches();
    }

}
