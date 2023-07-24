/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

import com.jah.flooring_mastery.dto.Product;
import com.jah.flooring_mastery.dto.Tax;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author drjal
 */
public class FlooringMasteryTaxDaoFileImplTest {

    FlooringMasteryTaxDao testDao;

    public FlooringMasteryTaxDaoFileImplTest() {
    }
        
    
     @Test
    public void testGetTax() throws Exception {
        //Create our method test inputs
        String taxTestFile = "SampleFileData/Data/Taxes.txt";
        //use the FileWriter to quickly blank the file
        new File(taxTestFile);

        testDao = new FlooringMasteryTaxDaoFileImpl(taxTestFile); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
  

        //Get order from the file
        Tax tax = testDao.getTax("Texas");
        
        // Check the data is equal
    assertEquals(tax.getStateName(),
                "Texas",
                "Checking state name.");
    assertEquals(tax.getStateAbbreviation(),
                "TX",
                "Checking state abb.");
    assertEquals(tax.getTaxRate(), 
                new BigDecimal("4.45"),
                "Checking Tax Rate.");

    }
    
        @Test
    public void testGetAllTaxes() throws Exception {
            //Create our method test inputs
            String taxTestFile = "SampleFileData/Data/Taxes.txt";
            //use the FileWriter to quickly blank the file
            new File(taxTestFile);

            testDao = new FlooringMasteryTaxDaoFileImpl(taxTestFile);//initialising the DAO 
            //this ensures we are starting with a fresh, empty DAO object

            Tax tax1 = testDao.getTax("Texas");
            Tax tax2 = testDao.getTax("California");
            Tax tax3 = testDao.getTax("Kentucky");
            Tax tax4 = testDao.getTax("Washington");

            // Retrieve the list of all taxes within the DAO
            List<Tax> allTaxes = testDao.getAllTax();

            // First check the general contents of the list
            assertNotNull(allTaxes, "The list of taxes must not null");
            assertEquals(4, allTaxes.size(), "List should have 4 taxes.");

            // Then the specifics
            assertTrue(allTaxes.contains(tax1),
                    "The list of products should include tax1.");
            assertTrue(allTaxes.contains(tax2),
                    "The list of products should include tax2.");
            assertTrue(allTaxes.contains(tax3),
                    "The list of products should include tax3.");
            assertTrue(allTaxes.contains(tax4),
                    "The list of products should include tax4.");
    }
}
