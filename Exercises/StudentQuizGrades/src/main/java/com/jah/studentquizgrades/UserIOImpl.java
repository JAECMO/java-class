/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.studentquizgrades;

import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class UserIOImpl implements UserIO {
private Scanner input = new Scanner(System.in);
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        String readString;
        System.out.println(prompt);
        readString = input.nextLine();
        return readString;
    }

    @Override
    public int readInt(String prompt) {
        Scanner inputReadInt = new Scanner(System.in);
        int readInt;
        System.out.println(prompt);
        readInt = inputReadInt.nextInt();
        return readInt;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        Scanner inputReadInt = new Scanner(System.in);
        int readInt;
        System.out.println(prompt + min + "-" +max);
        readInt = inputReadInt.nextInt();

        while (readInt < min || readInt > max) {
            System.out.println(prompt + min + "-" + max);
            readInt = inputReadInt.nextInt();
        }
        return readInt;
    }

    @Override
    public double readDouble(String prompt) {
        double readDouble;
        System.out.println(prompt);
        readDouble = input.nextDouble();
        return readDouble;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double readDouble;
        System.out.println(prompt + min + "-" + max);
        readDouble = input.nextDouble();

        while (readDouble <= min || readDouble >= max) {
            System.out.println(prompt + min + "-" + max);
            readDouble = input.nextDouble();
        }

        return readDouble;
    }

    @Override
    public float readFloat(String prompt) {
        float readFloat;
        System.out.println(prompt);
        readFloat = input.nextFloat();
        return readFloat;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float readFloat;
    System.out.println(prompt + min + "-" + max);
    readFloat = input.nextFloat();

    while (readFloat <= min || readFloat >= max) {
        System.out.println(prompt + min + "-" + max);
        readFloat = input.nextFloat();
    }

    return readFloat;
    }

    @Override
    public long readLong(String prompt) {
        long readLong;
        System.out.println(prompt);
        readLong = input.nextLong();
        return readLong;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long readLong;
    System.out.println(prompt + min + "-" + max);
    readLong = input.nextLong();

    while (readLong <= min || readLong >= max) {
        System.out.println(prompt + min + "-" + max);
        readLong = input.nextLong();
    }

    return readLong;
    }
    
}
