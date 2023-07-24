/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;

/**
 *
 * @author drjal
 */
public class ProductTypeValidationException extends Exception {
     
    public ProductTypeValidationException(String message) {
        super(message);
    }
    
    public ProductTypeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
