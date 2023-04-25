/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.interest_calculator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class Interest_Calculator {
    
    public static void main(String [] args) {
    Scanner input = new Scanner(System.in);
    Object[] numbers = new Object[3];
    String capitalString;
    String yearsString;
    String rateString;
    Double capitalDouble;
    int yearsInt;
    Double rateDouble;
    double[] arrayTotal;
    
    
        while (true) {
            boolean d = true;
            try {
                System.out.println("How much do you want to invest?: ");
                capitalString = input.nextLine();
                capitalDouble  = Double.parseDouble(capitalString);
                numbers[0] = capitalDouble;
                
                System.out.println("How many years are you investing?: ");
                yearsString = input.nextLine();
                d = isInteger(yearsString);
                yearsInt  = Integer.parseInt(yearsString);
                numbers[1] = yearsInt;
                
                
                System.out.println("What is the annual interest rate % growth?: ");
                rateString = input.nextLine();
                rateDouble  = Double.parseDouble(rateString);
                numbers[2] = rateDouble;

                for (Object number : numbers) {
                    if (Double.parseDouble(number.toString()) < 0) {
                        throw new NumberFormatException("");
                    }
                }
                 break;
            } catch (NumberFormatException e) {
            if (!d){
            System.out.println("Error: " + " please enter an integer for the years");
            }else{
                System.out.println("Invalid input. Please only enter positive numbers.");
            }
            }

        }//while
                arrayTotal = arrayCalculations(capitalDouble, yearsInt,rateDouble);
                displayArray(arrayTotal);
    }//main
    
    public static double[] arrayCalculations(double capitalDouble, int yearsInt, double rateDouble) {
        double[] newArray = new double[yearsInt+1];
        double j = 0;

        for (int i = 0; i < yearsInt+1; i++) {

            newArray[i] = capitalDouble * Math.pow(1 + (rateDouble / 400), 4*j);
            j++;
        }

        return newArray;
    }
    
    public static void displayArray(double [] array){
    DecimalFormat df = new DecimalFormat("#.##");
    double roundedPrincipal;
    double roundedEarnings;
    double roundedEndedPrincipal;
    
        for (int i = 0; i < array.length-1;i++){
      roundedPrincipal = Double.parseDouble(df.format(array[i]));
      roundedEarnings =  Double.parseDouble(df.format(array[i+1]-array[i]));
      roundedEndedPrincipal = Double.parseDouble(df.format(array[i+1]));
            System.out.println("Year "+ (i+1)+":" );
            System.out.println("Began with $" + roundedPrincipal);
            System.out.println("Earned $" + roundedEarnings);
            System.out.println("Ended with $" + roundedEndedPrincipal + "\n");
            
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
