/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.statecapitals3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author drjal
 */
public class StateCapitalsApp {
    public static void main(String[] args) throws IOException {
        
        Scanner input = new Scanner(System.in);
        HashMap<String, Capital> stateCapitalMap = new HashMap<>();
        
        Scanner sc = new Scanner(new BufferedReader(new FileReader("MoreStateCapitals.txt")));
// go through the file line by line
        while (sc.hasNextLine()) {
            
            String[] currentLine = sc.nextLine().split("::");
            Capital capital = new Capital(currentLine[1],Integer.parseInt(currentLine[2]),Double.parseDouble(currentLine[3])); 
            stateCapitalMap.put(currentLine[0],capital );
           
        }//while
        
        System.out.println(stateCapitalMap.size() + " STATES & CAPITALS ARE LOADED.");

        // get the set of state names from the map
        Set<String> stateNames = stateCapitalMap.keySet();
        System.out.println("========================================");

        // loop through the set and print out each state name
        for (String stateName : stateNames) {

            System.out.println(stateName + " - " + stateCapitalMap.get(stateName).getName() + " | Pop: " + stateCapitalMap.get(stateName).getPopulation()
                    + " | Area: " + stateCapitalMap.get(stateName).getSquareMileage() + " sq mi");

        }

        System.out.println("\n" + "Please enter the lower limit for capital city population:");
        int lowerLimit = input.nextInt();

        System.out.println("\n" +"LISTING CAPITALS WITH POPULATIONS GREATER THAN " + lowerLimit + ":");

        for (String stateName : stateNames) {

            if (stateCapitalMap.get(stateName).getPopulation() > lowerLimit) {
                System.out.println(stateName + " - " + stateCapitalMap.get(stateName).getName() + " | Pop: " + stateCapitalMap.get(stateName).getPopulation()
                        + " | Area: " + stateCapitalMap.get(stateName).getSquareMileage() + " sq mi");
            }//if
        }//for
        
        System.out.println("\n" + "Please enter the upper limit for capital city sq mileage:");
        double upperLimit = input.nextDouble();

        System.out.println("\n" +"LISTING CAPITALS WITH AREAS LESS THAN " + upperLimit + ":");

        for (String stateName : stateNames) {

            if (stateCapitalMap.get(stateName).getSquareMileage() < upperLimit) {
                System.out.println(stateName + " - " + stateCapitalMap.get(stateName).getName() + " | Pop: " + stateCapitalMap.get(stateName).getPopulation()
                        + " | Area: " + stateCapitalMap.get(stateName).getSquareMileage() + " sq mi");
            }//if
        }//for

    }
}
