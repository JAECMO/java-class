/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

/**
 *
 * @author drjal
 */
public class FlooringMasteryFileCreationException extends Exception {
   
    public FlooringMasteryFileCreationException(String message) {
        super(message);
    }
    
    public FlooringMasteryFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
