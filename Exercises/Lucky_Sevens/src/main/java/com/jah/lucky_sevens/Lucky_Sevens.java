/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.lucky_sevens;


import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class Lucky_Sevens {

    static Random rGen = new Random();
    static int dice1;
    static int dice2;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String moneyString;
        int moneyInt;
        int[] arrayMoney;
        int[] arrayTemp;
        int max;

        while (true) {

            try {
                System.out.println("How many dollars do you have? ");
                moneyString = input.nextLine();
                moneyInt = Integer.parseInt(moneyString);

                if (moneyInt < 0) {
                    throw new NumberFormatException("Invalid input. Please only enter a positive integer.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer number of dollars");
            }

        }//while

        arrayMoney = arrayMoneyCal(moneyInt);
        arrayTemp = buildTempArray(arrayMoney);
        max = findMax(arrayTemp);
        displayArray(arrayMoney, max);
    }//main

    public static int[] arrayMoneyCal(int moneyInt) {

        int[] arrayMoneyCal = new int[0];
        int diceSum = 0;
        int i = 0;

        while (moneyInt != 0) {

            dice1 = rGen.nextInt(6) + 1;
            dice2 = rGen.nextInt(6) + 1;

            diceSum = dice1 + dice2;

            //System.out.println(dice1 + " " + dice2);
            if (diceSum == 7) {
                moneyInt += 4;
            } else {
                moneyInt -= 1;
            }
            // System.out.println(moneyInt);
            arrayMoneyCal = GrowArray(arrayMoneyCal, 1);
            arrayMoneyCal[i] = moneyInt;
            i++;

        }//while

//           for (int j = 0; j < arrayMoneyCal.length; j++) {
//
//            System.out.println(arrayMoneyCal[j]);
//        }
        return arrayMoneyCal;
    }

    public static void displayArray(int[] array, int max) {

        int rolls = 0;

        System.out.println("You are broke after " + array.length + " rolls.");

        for (int i = 0; i < array.length; i++) {

            if (array[i] == max) {
                rolls = i + 1;
            }
        }

        System.out.println("You should have quit after " + rolls + " rolls when you had $" + max + ".");

    }

    public static int[] buildTempArray(int[] array) {

        int[] tempArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {

            tempArray[i] = array[i];

        }

        return tempArray;
    }

    public static int findMax(int[] array) {

        int max = 0;

        for (int i = 0; i < array.length - 1; i++) {

            if (array[i] > array[i + 1]) {
                max = array[i];
                array[i + 1] = array[i];
            } else {
                max = array[i + 1];
            }

        }

        return max;
    }

    public static int[] GrowArray(int[] original, int howManyMoreElements) {
        int[] newArray = new int[original.length + howManyMoreElements];

        for (int i = 0; i < original.length; i++) {
            // copy the element at the current index
            // from original to newArray
            newArray[i] = original[i];
            //System.out.println(newArray[i]);
        }

        return newArray;
    }
}
