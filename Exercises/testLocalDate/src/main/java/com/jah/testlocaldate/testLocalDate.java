/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.testlocaldate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author drjal
 */
public class testLocalDate {
    
    public static void main(String[] args) {
        String dateString = "31-12-1988";
        String dateFormatPattern = "dd-MM-yyyy";

        try {
            LocalDate date = LocalDate.parse(dateString);
            System.out.println("Parsed date: " + date);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format: " + dateFormatPattern);
        }
    }
    
}
