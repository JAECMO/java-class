/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.happyllamas;

import static com.jah.happyllamas.HappyLlamas.areTheLlamasHappy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class HappyLlamasTest {
    
    public HappyLlamasTest() {
     
    }

  //  @org.junit.jupiter.api.Test
  
         @Test
    public void testNormalTrampoline10() {
        // ARRANGE - for simple methods, this means setting up the parameters
        boolean isNasaFabric = false;
        int numTrampolines = 10;
        
        // ACT - for simple methods, this generally means calling the method under test
        // and then capturing its return to assert on
        boolean result = areTheLlamasHappy(isNasaFabric, numTrampolines);
        
        // ASSERT - basically just a conditional that proves the result is what
        // you expect it to be, plus an extra message to display if it doesn't match.
        //
        // There are a wide variety of assert types, here we
        // just want to assert that it returned false. But we could have also used
        // assertEquals and passed in a false value.
        
        assertFalse( result , "10 Llamas w/ Normal Trampolines should be Unhappy!" );
    }
    
    
}
