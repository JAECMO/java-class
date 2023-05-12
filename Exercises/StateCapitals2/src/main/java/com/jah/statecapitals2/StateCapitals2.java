/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.statecapitals2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author drjal
 */
public class StateCapitals2 {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        HashMap<String, String> stateCapitalMap = new HashMap<>();
       
        Scanner sc = new Scanner(new BufferedReader(new FileReader("StateCapitals.txt")));
// go through the file line by line
        while (sc.hasNextLine()) {
            
            String[] currentLine = sc.nextLine().split("::");
            stateCapitalMap.put(currentLine[0], currentLine[1]);
          
        }//while
        
        System.out.println(stateCapitalMap.size() + " STATES & CAPITALS ARE LOADED.");
        
        // get the set of state names from the map
        Set<String> stateNames = stateCapitalMap.keySet();
        System.out.println("=======");
        System.out.println("HERE ARE THE STATES :");
        
        // loop through the set and print out each state name
        int i = 1;
        for (String stateName : stateNames) {
            if ( i != stateNames.size()){
            System.out.print(stateName+", ");
            }else{
            System.out.print(stateName+".");
            }
            i++;
        }
        
        Object[] keys = stateNames.toArray();

        // Generate a random index between 0 and keys.length-1
        Random random = new Random();
        int randomIndex = random.nextInt(keys.length);

        // Select a random key from the array and print it
        String randomKey = (String) keys[randomIndex];
        System.out.println("Random key: " + randomKey);

        
        System.out.println("\n"+ "READY TO TEST YOUR KNOWLEDGE? WHAT IS THE CAPITAL OF "+ "'"+randomKey+"'" +"?");
        String readString = input.nextLine();
        
            if (stateCapitalMap.get(randomKey).equalsIgnoreCase(readString)) {
                System.out.println("NICE WORK! " + readString + " IS CORRECT!");

            } else {
                System.out.println("Wrong, better luck next time!");
            }

    }
}
