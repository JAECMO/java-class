/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.validation;

import java.sql.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author drjal
 */
public class DateValidator implements ConstraintValidator<DateValidate, Date> {
   

    @Override
    public void initialize(DateValidate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        if (date == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Date cannot be blank")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }

}