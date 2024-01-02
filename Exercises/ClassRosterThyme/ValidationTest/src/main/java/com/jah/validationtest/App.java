/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.validationtest;

/**
 *
 * @author drjal
 */
public class App {
    public static void main(String [] args) {
        
        String doubleText = "";
        String errorMessage = "error";
        double doubleValue = 0;
        
        
        try{
        doubleValue = Double.parseDouble(doubleText);
        System.out.println(doubleValue);
        }catch(NumberFormatException e){
        System.out.println(errorMessage);
        }
        
        
        
    }
    
}
