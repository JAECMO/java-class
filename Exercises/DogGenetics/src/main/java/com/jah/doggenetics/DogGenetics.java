/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.doggenetics;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class DogGenetics {
  
    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        String name;
        String[] breeds = {"St.Bernard", "Chihuahua", "Dramatic RedNosed Asian Pug", "Common Cur", "King Doberman"};
        int [] geneticsArray;

        System.out.println("What is your dog's name ? ");
        name = input.nextLine();
        geneticsArray=geneticsArray(breeds);
        display(name,geneticsArray,breeds);

    }
    
    public static int [] geneticsArray(String [] array){
        Random rGen = new Random();
        int[] geneticsArray = new int[array.length];
        int totalBreed = 0;
        int i=0;

        while (totalBreed != 100){
          
            
            geneticsArray[i] = rGen.nextInt(101-totalBreed) ;
            totalBreed += geneticsArray[i];
            
          
            if (i == array.length - 1 && totalBreed != 100) {
                emptyArray(geneticsArray);
                totalBreed=0;
                i = 0;
            } else {
                i++;
            }
        }//while
    
    return geneticsArray;
    
    }
    
    public static void display(String name, int [] geneticsArray, String [] breedsName) {
        String percent="";
        System.out.println("Well then, I have this highly reliable report on " + name + "'s prestigious background right here." + "\n");
        System.out.println(name + " is: " + "\n");

        for (int i = 0; i < geneticsArray.length; i++) {
            percent += geneticsArray[i] + "% " + breedsName[i] +"\n";
        }
        System.out.println(percent);
        System.out.println("Wow, that's QUITE the dog!");
    }
    public static void emptyArray(int[] array) {

        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }
      
}
