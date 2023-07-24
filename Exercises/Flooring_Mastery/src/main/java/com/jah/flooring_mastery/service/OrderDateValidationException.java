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
public class OrderDateValidationException extends Exception {
    
    public OrderDateValidationException(String message) {
        super(message);
    }
    
    public OrderDateValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
