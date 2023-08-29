/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.randomtest;

import java.util.Arrays;

/**
 *
 * @author drjal
 */
public class RandomTest {
       public static void main(String[] args) {
//        int[] intArray = { 1, 2, 3, 4, 5 };
//        String arrayAsString = intArrayToString(intArray, "");
//        
//        System.out.println(arrayAsString);

//       int nb = 3;
//       String result = "e:0:p:0";
//       result = addOneToParam(result,"e",nb);
//       System.out.println(result);

           int nb = partialMatch("3211", "1133");
           System.out.println(nb);
       
       }
    
    public static String intArrayToString(int[] array, String separator) {
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            
            if (i < array.length - 1) {
                builder.append(separator);
            }
        }
        
        return builder.toString();
    }
    
    
     private static String addOneToParam(String input, String param,int nb) {
        String[] segments = input.split(":");
        for (int i = 0; i < segments.length; i++) {
            if (segments[i].equals(param)) {
                int value = Integer.parseInt(segments[i + 1]);
                value += nb;
                segments[i + 1] = String.valueOf(value);
                break; // Assuming there's only one "e" segment
            }
        }
        
        return String.join(":", segments);
    }
     
     private static int partialMatch(String guess, String answer) {
    if (guess.length() != 4 || answer.length() != 4) {
        throw new IllegalArgumentException("Both input strings must have exactly 4 characters.");
    }

    int count = 0;
    boolean[] answerUsed = new boolean[4]; // To track which positions in 'answer' have been used

    for (int i = 0; i < 4; i++) {
        char guessChar = guess.charAt(i);

        if (guessChar != answer.charAt(i)) {
            for (int j = 0; j < 4; j++) {
                if (!answerUsed[j] && guessChar == answer.charAt(j)) {
                    count++;
                    answerUsed[j] = true;
                    break;
                }
            }
        }
    }

    return count;
}
}
