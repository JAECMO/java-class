/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Product;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author drjal
 */
public class FlooringMasteryProductDaoFileImplTest {

    FlooringMasteryProductDao testDao;

    public FlooringMasteryProductDaoFileImplTest() {
    }

     @Test
    public void testGetProduct() throws Exception {
        //Create our method test inputs
        String productsTestFile = "SampleFileData/Data/Products.txt";
        //use the FileWriter to quickly blank the file
        new File(productsTestFile);

        testDao = new FlooringMasteryProductDaoFileImpl(productsTestFile); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
  

        //Get order from the file
        Product product = testDao.getProduct("Wood");
        
        // Check the data is equal
    assertEquals(product.getProductType(),
                "Wood",
                "Checking product Type.");
    assertEquals(product.getCostPerSquareFoot(),
                new BigDecimal("5.15"),
                "Checking CostPerSquareFoot.");
    assertEquals(product.getLaborCostPerSquareFoot(), 
                new BigDecimal("4.75"),
                "Checking LaborCostPerSquareFoot.");

    }
    
        @Test
    public void testGetAllProduct() throws Exception {
            //Create our method test inputs
            String productsTestFile = "SampleFileData/Data/Products.txt";
            //use the FileWriter to quickly blank the file
            new File(productsTestFile);

            testDao = new FlooringMasteryProductDaoFileImpl(productsTestFile); //initialising the DAO 
            //this ensures we are starting with a fresh, empty DAO object

            Product prod1 = testDao.getProduct("Wood");
            Product prod2 = testDao.getProduct("Carpet");
            Product prod3 = testDao.getProduct("Tile");
            Product prod4 = testDao.getProduct("Laminate");

            // Retrieve the list of all products within the DAO
            List<Product> allProducts = testDao.getAllProduct();

            // First check the general contents of the list
            assertNotNull(allProducts, "The list of products must not null");
            assertEquals(4, allProducts.size(), "List should have 4 products.");

            // Then the specifics
            assertTrue(allProducts.contains(prod1),
                    "The list of products should include prod1.");
            assertTrue(allProducts.contains(prod2),
                    "The list of products should include prod2.");
            assertTrue(allProducts.contains(prod3),
                    "The list of products should include prod3.");
            assertTrue(allProducts.contains(prod4),
                    "The list of products should include prod4.");
    }
}
