/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary.ui;

import java.time.LocalDate;

/**
 *
 * @author drjal
 */
public interface UserIO {
    
    void print(String message);

    String readString(String prompt);
    
    LocalDate readDate(String prompt);
    
    String readMpaaRating(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
    
    boolean readboolean(String prompt);
    
}
