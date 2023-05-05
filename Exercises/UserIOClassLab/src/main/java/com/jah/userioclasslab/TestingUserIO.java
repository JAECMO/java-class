/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.userioclasslab;

/**
 *
 * @author drjal
 */
public class TestingUserIO {
    public static void main(String[] args) {
        
        UserIO userIO = new UserIOImpl();
        String str = userIO.readString("HEllo write something");
        userIO.print(str);
        int intRead = userIO.readInt("Enter int");
        userIO.print(""+intRead);
        int intRead2 = userIO.readInt("Enter number between : ", 5, 8);
        userIO.print(""+intRead2);
        // Example usage of readDouble() method
double doubleRead = userIO.readDouble("Enter a double between: ", 1.0, 2.0);
userIO.print(""+doubleRead);

// Example usage of readFloat() method
float floatRead = userIO.readFloat("Enter a float between: ", 0.0f, 1.0f);
userIO.print(""+floatRead);

// Example usage of readLong() method
long longRead = userIO.readLong("Enter a long between: ", -100L, 100L);
userIO.print(""+longRead);
    }
}
