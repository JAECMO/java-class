/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.rock_paper_scissors;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class Rock_Paper_Scissors {

    static int[] playerHumanWins = new int[1];
    static int[] playerComputerWins = new int[1];
    static int[] ties = new int[1];

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int rounds;
        String continueConditionString;
        Object[] inputs;
        inputs = inputs();
        boolean continueCondition = Boolean.valueOf(inputs[0].toString());
        rounds = Integer.parseInt(inputs[1].toString());

        while (rounds != 0 && continueCondition) {

            play();
            // System.out.println(rounds);
            rounds--;

            if (rounds == 0) {

                displayResults();
                winner();
                resetScore();

                while (true) {
                    System.out.println("Do you want to continue playing? ");
                    continueConditionString = input.nextLine();

                    if ((continueConditionString.trim().toLowerCase()).equals("yes")) {
                        inputs = inputs();
                        continueCondition = Boolean.valueOf(inputs[0].toString());
                        rounds = Integer.parseInt(inputs[1].toString());
                        break;
                    } else if ((continueConditionString.trim().toLowerCase()).equals("no")) {
                        System.out.println("Thanks for playing!");
                        continueCondition = false;
                        break;
                    } else {
                        System.out.println("Please answer by Yes or No");
                    }
                }//while

            }//if

        }//while

    }

    public static void resetScore() {
        ties[0] = 0;
        playerHumanWins[0] = 0;
        playerComputerWins[0] = 0;
    }

    public static void winner() {

        if (playerHumanWins[0] == playerComputerWins[0]) {
            System.out.println("It's a tie!");
        } else if (playerHumanWins[0] > playerComputerWins[0]) {
            System.out.println("Human player is the Winner!!");
        } else {
            System.out.println("Computer is the Winner!!");
        }

    }

    public static void displayResults() {
        System.out.println("ties : " + ties[0]);
        System.out.println("Human wins : " + playerHumanWins[0]);
        System.out.println("Computer wins : " + playerComputerWins[0]);
    }

    public static void play() {
        String humanChoiceString;
        int humanChoiceInt = 1;
        int computerChoice;
        boolean isInteger = false;
        Random rGen = new Random();
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Choose between 1 = Rock, 2 = Paper, 3 = Scissors ");
                humanChoiceString = input.nextLine();
                isInteger = isInteger(humanChoiceString);
                humanChoiceInt = Integer.parseInt(humanChoiceString);
                computerChoice = rGen.nextInt(3) + 1;
//                System.out.println("human "+humanChoiceInt);
//                System.out.println("computer "+computerChoice);
                if (humanChoiceInt < 1 || humanChoiceInt > 3) {
                    throw new NumberFormatException("");
                }
                break;
            } catch (NumberFormatException e) {
                if (!isInteger) {
                    System.out.println("Please enter an integer number");
                } else if (humanChoiceInt < 1 || humanChoiceInt > 3) {
                    System.out.println("Invalid input. Please only enter a number between 1 & 3.(1 = Rock, 2 = Paper, 3 = Scissors)");
                }

            }//try catch
        }//while

        if (humanChoiceInt == computerChoice) {

            System.out.println("It's a tie");
            ties[0] += 1;
        } else if (humanChoiceInt == 1 && computerChoice == 2) {
            System.out.println("Computer Wins");
            playerComputerWins[0] += 1;
        } else if (humanChoiceInt == 2 && computerChoice == 1) {
            System.out.println("Human Wins");
            playerHumanWins[0] += 1;
        } else if (humanChoiceInt == 1 && computerChoice == 3) {
            System.out.println("Human Wins");
            playerHumanWins[0] += 1;
        } else if (humanChoiceInt == 3 && computerChoice == 1) {
            System.out.println("Computer Wins");
            playerComputerWins[0] += 1;
        } else if (humanChoiceInt == 2 && computerChoice == 3) {
            System.out.println("Computer Wins");
            playerComputerWins[0] += 1;

        } else {
            System.out.println("Human Wins");
            playerHumanWins[0] += 1;
        }//if

    }

    public static Object[] inputs() {
        Object[] inputs = new Object[2];
        String roundsString;
        int roundsInt = 0;
        boolean isInteger = false;
        Scanner input = new Scanner(System.in);

        while (true) {

            try {

                System.out.println("how many rounds do you want to play (1-10)? ");
                roundsString = input.nextLine();
                isInteger = isInteger(roundsString);
                roundsInt = Integer.parseInt(roundsString);
                inputs[0] = true;
                inputs[1] = roundsInt;
                if (roundsInt < 1 || roundsInt > 10) {
                    throw new NumberFormatException("");
                }

                return inputs;
            } catch (NumberFormatException e) {

                if (!isInteger) {
                    System.out.println("Please enter an integer number");

                } else if (roundsInt < 1 || roundsInt > 10) {

                    System.out.println("Invalid input. You entered a number of rounds outside the range (1-10). Bye.");
                    inputs[0] = false;
                    return inputs;
                }
            }

        }//while input

    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
