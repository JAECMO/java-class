/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.healthyhearts;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class HealthyHearts {
     public static void main(String[] args) {
         
     Scanner input = new Scanner(System.in);
     String ageString;
     int ageInt = 0;
     int maxHeartRate;
     int [] targetArray;
     boolean isInteger = false;
     
         while (true) {

             try {
                 System.out.println("What is your age? ");
                 ageString = input.nextLine();
                 isInteger = isInteger(ageString);
                 ageInt = Integer.parseInt(ageString);
                 

                 if (ageInt < 1 || ageInt > 125) {
                     throw new NumberFormatException("");
                 }
                 break;
             } catch (NumberFormatException e) {

                 if (!isInteger) {
                     System.out.println("Please enter an integer number");
                 } else if (ageInt < 1 || ageInt > 125) {
                     System.out.println("Invalid input. Please only enter an age between 1 & 125.");
                 }
             }

        }//while
     maxHeartRate=maxhHeartRate(ageInt);
     targetArray=targetArray(maxHeartRate);
     display(maxHeartRate,targetArray);
     
     }
     
    public static int maxhHeartRate(int age) {

        int maxHeartRate = 220 - age;

        return maxHeartRate;
    }
    
    public static int [] targetArray(int maxHeartRate ) {
        DecimalFormat df = new DecimalFormat("#.##");
    double min = Math.round(maxHeartRate *0.5);
    double max = Math.round(maxHeartRate*0.85);
   
    Integer roundedMin;
    Integer roundedMax;
     
    roundedMin = Integer.parseInt(df.format(min));
    roundedMax = Integer.parseInt(df.format(max));
   
        int [] targetArray = {roundedMin, roundedMax };
    
        return targetArray;
    }
       public static void display(int maxHeartRate, int [] targetArray) {

        System.out.println("Your maximum heart rate should be "+ maxHeartRate + " beats per minute");
        System.out.println("Your target HR Zone is " + targetArray[0] + " - " + targetArray[1] + " beats per minute");

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
