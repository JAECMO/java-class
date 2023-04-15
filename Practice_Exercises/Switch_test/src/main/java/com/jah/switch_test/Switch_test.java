/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.switch_test;

/**
 *
 * @author drjal
 */
public class Switch_test {
        public static void main(String [] args) {
        int day = 4;
String dayType = "";

switch (day) {
    case 1:
    case 2:dayType = "Testing Day";
       break;
    case 3:
    case 4:dayType = "Correct Day";
       break;
    case 5:
        dayType = "Weekday";
        break;
    case 6:
    case 7:
        dayType = "Weekend";
            break;
    default:
        dayType = "Invalid Day";
}

System.out.println(dayType);
}
}
