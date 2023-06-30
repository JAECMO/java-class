/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dao;

import com.jah.vending_machine.dto.Item;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class VendingMachineDaoFileImplTest {
    
   
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    
   

    @Test
    public void testGetStudent() throws Exception {
 //Create our method test inputs
         String testFile = "testitemslist.txt";
        //use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
     //Create our method test inputs
      
 PrintWriter out = new PrintWriter(new FileWriter("testitemslist.txt"));
        String itemName1="Soda";
        out.println(itemName1+"::2.99::8");
        out.flush();
        out.close();
  
        
          // Get the item from the DAO
    Item retrievedItem = testDao.getItem("Soda");
     // Check the data is equal
     assertNotNull(retrievedItem,
                "item should not be null");
    assertEquals("Soda",retrievedItem.getName(),
                 "Check if names are similar");
    assertEquals(new BigDecimal("2.99"),retrievedItem.getCost(),
                 "Check if costs are similar");
    assertEquals(8,retrievedItem.getInventory(),
                 "Check if inventories are similar");
    
    }
    
    @Test
    public void testGetAllItems() throws Exception {
 //Create our method test inputs
         String testFile = "testitemslist.txt";
        //use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
     //Create our method test inputs.
     
     PrintWriter out = new PrintWriter(new FileWriter("testitemslist.txt"));
        String itemName1="Soda";
        String itemName2="Chips";
//        String itemName3="Candy";
        out.println(itemName1+"::2.99::8");
        out.println(itemName2+"::1.99::0");
//        out.println(itemName3+"::3.05::0");
        out.flush();
        out.close();
        
        // Retrieve the list of all students within the DAO
    List<Item> allItems = testDao.getAllItems();
    Item firstItem = testDao.getItem(itemName1);
    Item secondtItem = testDao.getItem(itemName2);
    
    // First check the general contents of the list
    assertNotNull(allItems, "The list of items must not be null");
    assertEquals(2, allItems.size(),"List of students should have 2 items.");

    // Then the specifics
    assertTrue(testDao.getAllItems().contains(firstItem),
                "The list of items should include Soda.");
    assertTrue(testDao.getAllItems().contains(secondtItem),
            "The list of items should include Chips.");
    }
     
    
}
