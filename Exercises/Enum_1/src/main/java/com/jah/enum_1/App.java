/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.enum_1;

import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class App {
    public static void main(String[] args) {
   
        Scanner input = new Scanner(System.in);

        String dayChoice = "";

         WeekDays day;
   
        while (true) {
            try {
                System.out.println("Enter a day of the week? ");
                dayChoice = input.nextLine().toUpperCase();
                day = WeekDays.valueOf(dayChoice);
                break;
            } catch (Exception e) {
                System.out.println("Wrong entry");;
            }
        }
        
         System.out.println(calculate(day) + " days are left before Friday");

    }
    
     public static int calculate(WeekDays day) {

        switch(day) {
            case MONDAY:
                return 4;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 1;
            case FRIDAY:
                return 0;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 5;
            default:
                throw new UnsupportedOperationException();
        }
    }
    
}
