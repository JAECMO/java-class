/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.summativesums;

/**
 *
 * @author drjal
 */
public class SummativeSums {

    static int counter = 0;

    public static void main(String[] args) {
        int[] arraySum;

        int[] array1 = {1, 90, -33, -55, 67, -16, 28, -55, 15};
        int[] array2 = {999, -60, -77, 14, 160, 301};
        int[] array3 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
            140, 150, 160, 170, 180, 190, 200, -99};

        arraySum = arraySum(array1);
        displayArray(arraySum);

        arraySum = arraySum(array2);
        displayArray(arraySum);

        arraySum = arraySum(array3);
        displayArray(arraySum);
    }

    public static int[] arraySum(int[] array) {
        int arrayTotal = 0;
        counter++;

        for (int i = 0; i < array.length; i++) {
            arrayTotal += array[i];
        }

        int[] arraySum = {arrayTotal, counter};

        return arraySum;
    }

    public static void displayArray(int[] arraySum) {

        System.out.println("#" + arraySum[1] + " Array Sum: " + arraySum[0]);

    }
}
