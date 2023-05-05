/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.studentquizgrades;

import java.util.*;


/**
 *
 * @author drjal
 */
public class StudentQuizGrades {
    private final static int QUIZ_SIZE = 3;
    private static List<Integer> scores = new ArrayList<>();
    private final static Map<String, List<Integer>> students = new HashMap<>();;
    private final static Scanner input = new Scanner(System.in);
    private static final UserIO userIO = new UserIOImpl();
   
    
    public static int menu() {
		String choice;
		String option="**** MENU ****";
		option+="\n\n1-viewStudents \n2-addStudent\n3-removeStudent\n4-viewQuizScores\n5-viewAverageQuizScore\n6-Quit\n\n";
		option+="Enter your choice: ";
		String msg="";
		do {
			option+=msg;
                        //userIO.print(option);
			choice=userIO.readString(option);
			msg="\nYou need to enter a number between [1-6] !!!";
		}while(!"1".equals(choice) && !"2".equals(choice) && !"3".equals(choice)&& !"4".equals(choice) && !"5".equals(choice) && !"6".equals(choice));
		return Integer.parseInt(choice);
	}//menu
    
        public static void viewStudents() {
        userIO.print("List of students:");
        if(!students.isEmpty())
        for (String name : students.keySet()) {
            userIO.print(name);
        }else{
            userIO.print("There are no students");
        }
    }

    public static void addStudent() {
  //      Scanner input2 = new Scanner(System.in);
//        userIO.print("Enter name of new student: ");
        int score;
        String[] count = {"first", "second", "third"};
        String nameEntered = userIO.readString("Enter name of new student: ");
        boolean stop = false;
        int counting = 0;
 
        
     if(!students.isEmpty()) {  
      while(!stop) {
        
        for (String name : students.keySet()) {
          
                if (nameEntered.equals(name)) {
                    nameEntered = userIO.readString("Name already exists, enter another name: ");
                    

                } else if (counting == students.size()) {
                    students.put(nameEntered, new ArrayList<>());
                    stop = true;
                } 
                counting++;
            }//for
                counting = 1;
        }   //while         

     }else{
     students.put(nameEntered, new ArrayList<>());
     }                
   
        for (int i = 1; i <= QUIZ_SIZE; i++) {
           // userIO.print("What's the " + count[i - 1] + " score of " + nameEntered);
            score = userIO.readInt("What's the " + count[i - 1] + " score of " + nameEntered, 0,20);
            scores.add(score);
            students.put(nameEntered, scores);
        }
        

   
        userIO.print("Student added.");
    }

    public static void removeStudent() {
        userIO.print("Enter name of student to remove: ");
        String name = input.nextLine();
        if (students.containsKey(name)) {
            students.remove(name);
            userIO.print("Student removed.");
        } else {
            userIO.print("Student not found.");
        }
    }

    public static void viewQuizScores() {
        String name = userIO.readString("Enter name of student to view quiz scores: ");
         scores = students.get(name);
        if (scores != null) {
            userIO.print(name + "'s quiz scores:");
            for (int score : scores) {
                userIO.print(""+score);
            }
        } else {
            userIO.print("Student not found.");
        }
    }

    public static void viewAverageQuizScore() {
        String name = userIO.readString("Enter name of student to view average quiz score: ");
       scores = students.get(name);
        if (scores != null) {
            int total = 0;
            for (int score : scores) {
                total += score;
            }
            double average = (double) total / scores.size();
            userIO.print(name + "'s average quiz score: " + String.format("%.2f",average));
        } else {
            userIO.print("Student not found.");
        }
    }
    
    
    public static void main(String[] args) {
        userIO.print(""+students.size());
        
        // Add two students with three quiz scores each
        List<Integer> scores1 = new ArrayList<>(Arrays.asList(85, 90, 92));
        List<Integer> scores2 = new ArrayList<>(Arrays.asList(76, 88, 95));
       students.put("Jack", scores1);
       students.put("Martha", scores2);
        
        
     int choice;
        choice = menu();
        while (choice != 6) {
            switch (choice) {
               case 1:
                    viewStudents();
                    
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    viewQuizScores();
                    break;
                case 5:
                    viewAverageQuizScore();
                    break;  
            }
            choice = menu();
        }//while
        userIO.print( "Thank you!");
    }
}