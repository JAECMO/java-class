/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.enum_2;

import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class App {
    
    public static void main(String[] args) {
   
        Scanner input = new Scanner(System.in);

        String opChoice;
        int op1;
        int op2;

         MathOperator op;
   
        while (true) {
            try {
                System.out.println("Enter the opreation of your choice PLUS, MINUS, MULTIPLY, DIVIDE? ");
                opChoice = input.nextLine().toUpperCase();
                op = MathOperator.valueOf(opChoice);
                break;
            } catch (Exception e) {
                System.out.println("Wrong entry");
            }
        }
       
        while (true) {
            try {
                System.out.println("Enter your first operand (integer) ? ");
                op1 = input.nextInt();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong entry");
            }
        }

        while (true) {
            try {
                System.out.println("Enter your second operand (integer) ? ");
                op2 = input.nextInt();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong entry");
            }
        }
        
        IntMath calculation = new IntMath();
        
         System.out.println("The answer is " + calculation.calculate(op, op1, op2));

    }
    
    
    
    

  
   
    
}
