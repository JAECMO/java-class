/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.states_test;

/**
 *
 * @author drjal
 */
public class App {

    public static void main(String[] args) {

        String stateLongName = "";
        for (States state : States.values()) {
                    if (state.getAbbreviation().equalsIgnoreCase("TX")) {
                        stateLongName = state.name();
                    }
          //  System.out.println(state.name());
        }
        System.out.println(stateLongName);
    }
}
