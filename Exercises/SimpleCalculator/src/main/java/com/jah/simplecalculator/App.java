/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.simplecalculator;

import javax.swing.JOptionPane;

/**
 *
 * @author drjal
 */
public class App {
     static int operandsNb = 2;
     static double [] operands = new double[2];
     static double total;
     static String addMark = "addition", subsMark = "substraction", multMark = "multiplication", divMark = "division";
     
    public static int menu() {
		String choice;
		String option="**** MENU ****";
		option+="\n\n1-Addition \n2-Substraction\n3-Multiplication\n4-Division\n5-Quit\n\n";
		option+="Enter your choice: ";
		String msg="";
		do {
			option+=msg;
			choice=JOptionPane.showInputDialog(null,option,"Simple Calculator",JOptionPane.PLAIN_MESSAGE);
			msg="\nYou need to enter a number between [1-5] !!!";
		}while(!"1".equals(choice) && !"2".equals(choice) && !"3".equals(choice)&& !"4".equals(choice) && !"5".equals(choice));
		return Integer.parseInt(choice);
	}//menu
    
    public static void addition() {
        operandsRequest(addMark);
        total = SimpleCalculator.add(operands[0],operands[1]);
        displayResult(addMark,total);
    }

    public static void substraction() {
        operandsRequest(subsMark);
        total = SimpleCalculator.subs(operands[0],operands[1]);
        displayResult(subsMark,total);
    }

    public static void multiplication() {
        operandsRequest(multMark);
        total = SimpleCalculator.mult(operands[0], operands[1]);
        displayResult(multMark, total);
    }

    public static void division() {
        operandsRequest(divMark);
        total = SimpleCalculator.div(operands[0], operands[1]);
        displayResult(divMark, total);
    }
    
    public static void operandsRequest(String mark) {

        String option = "";
        String[] options = {" first ", " second "};

        for (int i = 0; i < operandsNb; i++) {
            option += "Enter the";
            option += options[i] + "operand";
            operands[i] = Integer.parseInt(JOptionPane.showInputDialog(null, option, mark , JOptionPane.PLAIN_MESSAGE));
            option = "";
        }

    }
    
    public static void displayResult(String mark, double total) {
        
        JOptionPane.showMessageDialog(null, "The total of the " + mark  + " is "+ String.format("%.2f",total));
    
    }
    
    
    	public static void main(String[] args) {

        int choice;
        choice = menu();
        while (choice != 5) {
            switch (choice) {
                case 1:
                    addition();
                    break;
                case 2:
                    substraction();
                    break;
                case 3:
                    multiplication();
                    break;
                case 4:
                    division();
                    break;
                
            }
            choice = menu();
        }//while
        JOptionPane.showMessageDialog(null, "Thank you!");
    }

}
