/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.arrays;

import java.util.Random;

/**
 *
 * @author drjal
 */
public class Arrays {
    
    public static void main(String [] args) {
        int[] numbers = {3, 5, 2, 0};
        int[] numbers2 = {1, 2, 3, 4, 5, 6};
        jaggedArray();
        oddNumberReverse(numbers);
        pairSkipOneElement(numbers2);
       
      int [] newArray =  GrowArray(numbers,5);
     
      String[] hidingSpots = new String[100];
        Random squirrel = new Random();
        hidingSpots[squirrel.nextInt(hidingSpots.length)] = "Nut";
        System.out.println("The nut has been hidden ...");
        
        System.out.println(squirrel.nextInt(hidingSpots.length));//Not same random number than previously
        
        for (int i = 0; i < hidingSpots.length; i++) {
            try {
                if (hidingSpots[i].equals("Nut")) {
                    System.out.print("Found it! It's in spot# " + i);
                }
            } catch (NullPointerException exception) { //Could be Also Exception instead of Exception

            }

        }
            
   }


       
       
    
     public static void jaggedArray() {
        String[][] strArray = {{"This", "is"}, {"a", "test", "of", "jagged arrays"}};

        for (int i = 0; i < strArray.length; i++) {
            //System.out.println(strArray.length);
            for (int j = 0; j < strArray[i].length; j++) {
               System.out.print( strArray[i][j] + " ");
            }
             
        }
        // System.out.println(strArray[0][1]);
    }
     
     public static void oddNumberReverse(int [] numbers){
         
    // start at last index, go to first (0)
    for (int i = numbers.length - 1; i >= 0; i--) {
        if (i % 2 == 1) {
            System.out.println("index " + i + " - " + numbers[i]);
        }
    }
     }
     
     public static void pairSkipOneElement(int [] numbers){
      

    for (int i = 0; i < numbers.length - 2; i += 3) {
        System.out.println("Pair: (" + numbers[i] + ", " + numbers[i + 1] + ")");
    }
     }
public static int[] GrowArray(int[] original, int howManyMoreElements)
{
    int[] newArray = new int[original.length + howManyMoreElements];

    for (int i = 0; i < original.length; i++)
    {
        // copy the element at the current index
        // from original to newArray
        newArray[i] = original[i];
        System.out.println(newArray[i]);
    }

    return newArray;
}

}
