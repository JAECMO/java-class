/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.unittesting.arrays;

import static com.tsg.unittesting.arrays.ArrayExerciseA.maxOfArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class ArrayExerciseATest {
    
   @Test
    public void testOneElementArray() {

    
        // ARRANGE
        int numbers[] = {1};
        
        // ACT
        int max = maxOfArray(numbers);
        
        // ASSERT - w/ Message
        int expectedMax= 1;
        assertEquals(expectedMax, max, "Expected 1 as Maximum value of the array");
    }
    
     @Test
    public void testFourElementArray() {

    
        // ARRANGE
        int numbers[] = {1,15,2,6};
        
        // ACT
        int max = maxOfArray(numbers);
        
        // ASSERT - w/ Message
        int expectedMax = 15;
        assertEquals(expectedMax, max, "Expected 15 as Maximum value of the array");
    }
    
    @Test
    public void testFourNegativeElementArray() {

    
        // ARRANGE
        int numbers[] = {1,-15,8,6};
        
        // ACT
        int max = maxOfArray(numbers);
        
        // ASSERT - w/ Message
        int expectedMax = 8;
        assertEquals(expectedMax, max, "Expected 8 as Maximum value of the array");
    }
    
}
