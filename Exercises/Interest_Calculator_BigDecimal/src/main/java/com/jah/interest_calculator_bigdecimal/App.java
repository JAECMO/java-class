/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.interest_calculator_bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class App {
    
     public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Object[] numbers = {0, 0, 0};
        String capitalString;
        String yearsString;
        String rateString;
        BigDecimal capitalBigDecimal;
        BigDecimal yearsBigDecimal;
        BigDecimal rateBigDecimal;
        BigDecimal[] arrayTotal;

        while (true) {
            try {
                System.out.println("How much do you want to invest?: ");
                capitalString = input.nextLine();
                capitalBigDecimal = new BigDecimal(capitalString);
                numbers[0] = capitalBigDecimal;
                entryVerification(numbers);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please only enter positive numbers.");
            }
        }//while
        
         while (true) {
             boolean d = true;
             try {
                 System.out.println("How many years are you investing?: ");
                 yearsString = input.nextLine();
                 d = isInteger(yearsString);
                 int yearsBigDecimalTest = Integer.parseInt(yearsString);
                 yearsBigDecimal = new BigDecimal(yearsString);
                 numbers[1] = yearsBigDecimal;
                 entryVerification(numbers);
                 break;
             } catch (NumberFormatException e) {
                 if (!d) {
                     System.out.println("Error: " + " please enter an integer for the years");
                 } else {
                     System.out.println("Invalid input. Please only enter positive numbers.");
                 }

             }
         }//while
        
        

        while (true) {
            try {
                System.out.println("What is the annual interest rate % growth?: ");
                rateString = input.nextLine();
                rateBigDecimal = new BigDecimal(rateString);
                numbers[2] = rateBigDecimal;
                entryVerification(numbers);

                break;
            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please only enter positive numbers.");
            }

        }//while
        arrayTotal = arrayCalculations(capitalBigDecimal, yearsBigDecimal, rateBigDecimal);
        displayArray(arrayTotal);
    }//main

    public static void entryVerification(Object numbers[]) {

        for (Object number : numbers) {
            if (Double.parseDouble(number.toString()) < 0) {
                throw new NumberFormatException("");
            }
        }

    }

    public static BigDecimal[] arrayCalculations(BigDecimal capitalBigDecimal, BigDecimal yearsBigDecimal, BigDecimal rateBigDecimal) {
        BigDecimal[] newArray = new BigDecimal[yearsBigDecimal.add(BigDecimal.ONE).intValue()];
        int j = 0;

        for (int i = 0; i < yearsBigDecimal.intValue() + 1; i++) {
            BigDecimal op1 = (rateBigDecimal.divide(new BigDecimal("400"),10,RoundingMode.HALF_UP )).add(new BigDecimal("1"));

            BigDecimal powBig = op1.pow(4 * j);

            newArray[i] = capitalBigDecimal.multiply(powBig).setScale(2, RoundingMode.HALF_UP);
            j++;
        }

        return newArray;
    }

    public static void displayArray(BigDecimal[] array) {
        BigDecimal principal;
        BigDecimal earnings;
        BigDecimal endedPrincipal;

        for (int i = 0; i < array.length - 1; i++) {
            principal = array[i];
            earnings = array[i + 1].subtract(array[i]);
            endedPrincipal = array[i + 1];
            
            System.out.println("Year " + (i + 1) + ":");
            System.out.println("Began with $" + principal);
            System.out.println("Earned $" + earnings);
            System.out.println("Ended with $" + endedPrincipal + "\n");

        }

    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
