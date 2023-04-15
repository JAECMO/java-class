/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.factorizer;

import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class Factorizer {
    public static void main(String [] args) {
        // declare variables
        String numberString;
      
        int [] factors;
        String perfectNumberOrNo;
        String primeNumberOrNo;
        Scanner myScanner = new Scanner(System.in);
        
        while (true){
        try {
            System.out.println("What number would you like to factor? ");
            numberString = myScanner.nextLine();
            int number = Integer.parseInt(numberString);
            if(number !=0){
            factors = factorize(number);
            perfectNumberOrNo = perfectNumber(factors,number);
            primeNumberOrNo = primeNumber(factors,number);
            displayArray(factors, numberString,perfectNumberOrNo,primeNumberOrNo);
            }else{
                System.out.println("The factors of " + number + " are infinite ");
                System.out.println(number + " has" + " an infinite number of factors.");
                System.out.println(number + " is not a perfect number.");
                System.out.println(number + " is not a prime number.");
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer.");
        }
        }
        
    }
    public static int[] factorize(int number){
         int j = 0;
        
        int [] factors = new int[0];
       
        if (number < 0){
            
              for (int i = number; i <= -number; i++) {
                if (i == 0) {
                    continue;
                }
                if (number % i == 0) {
                    factors = GrowArray(factors, 1);
                    factors[j] = i;
                    j++;
                }

            }
   
        }else if (number > 0){
               for (int i = 1; i <= number; i++) {

            if (number % i == 0) {
                factors=GrowArray(factors,1);
                factors[j] = i;
                j++;
            }

        }
 
        }
        
    return factors;
    }
    
    public static String primeNumber(int[] array, int number){
    String response= " is not";
       
    if (number > 1 && array.length == 2){
        response=" is";
        }
    
    return response;
    }
    
    public static int[] GrowArray(int[] original, int howManyMoreElements)
{
    int[] newArray = new int[original.length + howManyMoreElements];

    for (int i = 0; i < original.length; i++)
    {
        // copy the element at the current index
        // from original to newArray
        newArray[i] = original[i];
        //System.out.println(newArray[i]);
    }

    return newArray;
}
    
    public static String perfectNumber(int [] array, int number){
        String response = " is not";
        int total = 0;
        if (number > 0) {
            for (int i = 0; i < array.length - 1; i++) {
                total += array[i];
            }
        } else {

            for (int i = 1; i < array.length; i++) {
                total += array[i];
            }

        }
        if (total == number) {
            response = " is";
        }
 
        return response;
    }
    
    public static void displayArray(int[] array, String number, String perfectNumberOrNo, String primeNumberOrNo){
    
        System.out.println("The factors of " + number + " are : ");

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("\n" + number + " has " + array.length + " factors.");
        System.out.println(number + perfectNumberOrNo + " a perfect number.");
        System.out.println(number + primeNumberOrNo + " a prime number.");
    }
}
