/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.birthday_calculator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class App {
    
        public static void main(String [] args) {
            
        Scanner input = new Scanner(System.in);

        String entry;
        String weekDayBirthday;
        String weekDayYear;
        String ageReply;
        long daysLeft;
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate birthday;
        
            while (true) {
                try {
                    System.out.println("What's your birthday? (dd/MM/yyyy) ");
                    entry = input.nextLine();
                    birthday = LocalDate.parse(entry, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong entry");
                }
            }
            
            weekDayBirthday = weekDayBirth(birthday);
            weekDayYear = weekDayYear(today, birthday);
            daysLeft = daysLeft(today, birthday);
            ageReply = ageReply(today,birthday);
            
            System.out.println("Welcome to the Magical BirthDAY Calculator!");
            System.out.println("\n"+"That's means you were born on a " +weekDayBirthday.toUpperCase());
            System.out.println("This year it falls on a " +weekDayYear.toUpperCase()+"...");
            System.out.println("And since today is " + formattedToday +", there's only "+  daysLeft + " more days until the next one!");
            System.out.println(ageReply);

        }
        
       public static String weekDayBirth(LocalDate birthday) {
        String weekDayBirth;
        String[] array = arrayDate(birthday);
        weekDayBirth = array[0];
        return weekDayBirth;

    }
     
    public static String weekDayYear(LocalDate currentDate, LocalDate birthday) {
        String weekDayYear;
        int yearsDiff = currentDate.getYear() - birthday.getYear();
        LocalDate currentYearBirthDay = birthday.plusYears(yearsDiff);
        weekDayYear = arrayDate(currentYearBirthDay)[0];

        return weekDayYear;

    }

    public static String[] arrayDate(LocalDate date) {
        String formatted;
        String arrayDate[];

        formatted = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

        arrayDate = formatted.split(",");

        return arrayDate;
    }
    
    public static long daysLeft(LocalDate currentDate, LocalDate birthday) {
        long daysLeft;
        int yearsDiff = currentDate.getYear() - birthday.getYear();
       // System.out.println(yearsDiff);
        LocalDate currentYearBirthDay = birthday.plusYears(yearsDiff);
        Period diff = currentDate.until(currentYearBirthDay);
       // System.out.println(currentYearBirthDay);
        
        if (diff.getDays() < 0) {
           currentYearBirthDay = currentYearBirthDay.plusYears(1);
        }
        
        daysLeft = ChronoUnit.DAYS.between(currentDate, currentYearBirthDay);

        return daysLeft;
    }
    
    public static String ageReply(LocalDate currentDate, LocalDate birthday) {
        String ageReply;
        int age = currentDate.getYear() - birthday.getYear();
        LocalDate currentYearBirthDay = birthday.plusYears(age);

        Period diff = currentDate.until(currentYearBirthDay);

       // System.out.println(diff.getDays());

        if (diff.getDays() <= 0) {
            ageReply = "Bet yer happy to have turned " + age;
        } else {
            ageReply = "Bet yer excited to be turning " + age + "!";
        }

        return ageReply;
    }

}
