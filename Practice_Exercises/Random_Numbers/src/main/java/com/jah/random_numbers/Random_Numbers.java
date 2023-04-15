/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.random_numbers;

import java.util.Random;

/**
 *
 * @author drjal
 */
public class Random_Numbers {
    
    public static void main(String [] args) {
    Random rGen = new Random();
    for (int i=0;i<50;i++){ 
        int rInt = rGen.nextInt(10);
        System.out.print(rInt + "\n");
    }
    
Random rGen2 = new Random();
int rInt2 = rGen2.nextInt();
 System.out.print(rInt2 + "\n");

    
  //  Random rng = new Random();

// this will generate a random double value from 0.0 (inclusive)
// to 1.0 (exclusive)

double randomValue = rGen.nextDouble();
 System.out.print("randomValue = " + randomValue);
 
 
// maximum is 13.2
// minimum is -7.5

for (int i=0;i<50;i++){ 
        double desiredOutput = rGen.nextDouble() * (13.2 - -7.5) + -7.5;
        System.out.print(desiredOutput + "\n");
    }

 int max=100,min=50;
          System.out.println("Generated numbers are within "+min+" to "+max);
        
          System.out.println(rGen.nextInt(max - min + 1) + min);
          System.out.println(rGen.nextInt(max - min + 1) + min);
          System.out.println(rGen.nextInt(max - min + 1) + min);
          
           System.out.println(min + (int)(Math.random() * ((max - min) + 1)));
          System.out.println(min + (int)(Math.random() * ((max - min) + 1)));
          System.out.println(min + (int)(Math.random() * ((max - min) + 1)));
          
    int start = 2;
    int stop = 10;

    for(int i = start; i < stop; i++){
        System.out.println("beep!");
    }
    }
}
