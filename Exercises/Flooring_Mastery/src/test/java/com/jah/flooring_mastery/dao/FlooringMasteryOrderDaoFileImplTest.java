/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

import com.jah.flooring_mastery.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author drjal
 */
public class FlooringMasteryOrderDaoFileImplTest {
    
     FlooringMasteryOrderDao testDao;
    
    public FlooringMasteryOrderDaoFileImplTest() {
    }
    
    @Test
    public void testAddGetOrder() throws Exception {
        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
 
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }

        testDao = new FlooringMasteryOrderDaoFileImpl(orderTestFolder, backupTestFolder); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
        //Create our method test input

        String dateString = "2023-07-28"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);

        int orderNumber = 1;

        Order order1 = new Order(orderDate, orderNumber, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        //Add order to the file
        testDao.createOrder(orderDate, order1, orderNumber);


        // Get the order from the DAO
        Order retrievedOrder = testDao.getOrder(orderNumber, orderDate);
        // Check the data is equal
        assertNotNull(retrievedOrder,
                "item should not be null");
        assertEquals("John Smith", retrievedOrder.getCustomerName(),
                "Check if names are similar");
        assertEquals("TX", retrievedOrder.getState(),
                "Check if states are similar");
        assertEquals(new BigDecimal("4.45"), retrievedOrder.getTaxRate(),
                "Check if tax rates are similar");
        assertEquals("Carpet", retrievedOrder.getProductType(),
                "Check if products are similar");
        assertEquals(new BigDecimal("100.00"), retrievedOrder.getArea(),
                "Check if area are similar");
        assertEquals(new BigDecimal("2.25"), retrievedOrder.getCostPerSquareFoot(),
                "Check if cost per Sq ft are similar");
        assertEquals(new BigDecimal("2.10"), retrievedOrder.getLaborCostPerSquareFoot(),
                "Check if labor cost per Sq ft are similar");
        assertEquals(new BigDecimal("225.00"), retrievedOrder.getMaterialCost(),
                "Check if material cost are similar");
        assertEquals(new BigDecimal("210.00"), retrievedOrder.getLaborCost(),
                "Check if labor cost are similar");
        assertEquals(new BigDecimal("19.36"), retrievedOrder.getTax(),
                "Check if taxes are similar");
        assertEquals(new BigDecimal("454.36"), retrievedOrder.getTotal(),
                "Check if totals are similar");

    }
    
    
    @Test
    public void testAddGetAllOrdersByDate() throws Exception {
        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
       
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }

        testDao = new FlooringMasteryOrderDaoFileImpl(orderTestFolder, backupTestFolder); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
        //Create our method test input

        String dateString = "2023-07-28"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);

        int orderNumber1 = 1;
        int orderNumber2 = 2;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2 = new Order(orderDate, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        // Add both our orders to the DAO
        testDao.createOrder(orderDate, order1, orderNumber1);
        testDao.createOrder(orderDate, order2, orderNumber2);

        // Retrieve the list of all orders by Date within the DAO
        List<Order> allOrdersByDate = testDao.getOrdersByDate(orderDate);

        // First check the general contents of the list
        assertNotNull(allOrdersByDate, "The list of orders must not null");
        assertEquals(2, allOrdersByDate.size(), "List of orders should have 2 students.");

        // Then the specifics
        assertTrue(allOrdersByDate.contains(order1),
                "The list of orders should include order1.");
        assertTrue(allOrdersByDate.contains(order2),
                "The list of orders should include order2.");
    }
    
     @Test
      public void testRemoveOrder() throws Exception {
         String orderTestFolder = "SampleFileDataTest/Orders/";
         String backupTestFolder = "SampleFileDataTest/Backup/";
         
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }

        testDao = new FlooringMasteryOrderDaoFileImpl(orderTestFolder, backupTestFolder); //initialising the DAO 
        //this ensures we are starting with a fresh, empty DAO object
        //Create our method test input

        String dateString = "2023-07-28"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);

        int orderNumber1 = 1;
        int orderNumber2 = 2;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2 = new Order(orderDate, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        // Add both our orders to the DAO
        testDao.createOrder(orderDate, order1, orderNumber1);
        testDao.createOrder(orderDate, order2, orderNumber2);
        
            // remove the first order- order1
    Order removedOrder = testDao.removeOrder(orderNumber1, orderDate);

    // Check that the correct object was removed.
    assertEquals(removedOrder, order1, "The removed dvd should be dvd1.");

    // Get all the orders
    List<Order> allOrdersByDate = testDao.getOrdersByDate(orderDate);

    // First check the general contents of the list
    assertNotNull( allOrdersByDate, "All orders list should be not null.");
    assertEquals( 1, allOrdersByDate.size(), "All orders should only have 1 order.");

    // Then the specifics
    assertFalse( allOrdersByDate.contains(order1), "All orders should NOT include order1.");
    assertTrue( allOrdersByDate.contains(order2), "All orders should include order2.");    

    // Remove the second order
    removedOrder = testDao.removeOrder(orderNumber2, orderDate);
    // Check that the correct object was removed.
    assertEquals( removedOrder, order2, "The removed order should be order2.");

    // retrieve all of the orders again, and check the list.
    allOrdersByDate = testDao.getOrdersByDate(orderDate);

    // Check the contents of the list - it should be empty
    assertTrue( allOrdersByDate.isEmpty(), "The retrieved list of orders should be empty.");

    // Try to 'get' both orders by their old number and Date - they should be null!
    Order retrievedOrder = testDao.getOrder(orderNumber1, orderDate);
    assertNull(retrievedOrder, "order1 was removed, should be null.");

    retrievedOrder = testDao.getOrder(orderNumber2, orderDate);
    assertNull(retrievedOrder, "order2 was removed, should be null.");
          
      }
     
//     @Test
//      public void testExportAllOrders() throws Exception {
//        String orderTestFolder = "SampleFileDataTest/Orders/";
//        String backupTestFolder = "SampleFileDataTest/Backup/";
//        //use the FileWriter to quickly blank the file
//        File directory = new File(orderTestFolder);
//        File directoryBackUp= new File(backupTestFolder);
//        //deletes all the files in the orderTestFolder
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            file.delete();
//        }
//        File[] filesB = directoryBackUp.listFiles();
//        for (File file : filesB) {
//            file.delete();
//        }
//
//        testDao = new FlooringMasteryOrderDaoFileImpl(orderTestFolder, backupTestFolder); //initialising the DAO 
//        //this ensures we are starting with a fresh, empty DAO object
//        //Create our method test input
//
//         String dateString1 = "2023-07-28"; // Replace this with your date string
//         String dateString2 = "2023-07-29"; // Replace this with your date string
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//         LocalDate orderDate1 = LocalDate.parse(dateString1, formatter);
//         LocalDate orderDate2 = LocalDate.parse(dateString2, formatter);
//
//        int orderNumber1 = 1;
//        int orderNumber2 = 2;
//
//        Order order1 = new Order(orderDate1, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
//        Order order2 = new Order(orderDate2, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));
//
//        // Add both our orders to the DAO (different dates -> 2 files)
//        testDao.createOrder(orderDate1, order1, orderNumber1);
//        testDao.createOrder(orderDate2, order2, orderNumber2);
//        
//        List<Order> allOrdersList = testDao.exportAllData();
//      
//         // First check the general contents of the list
//         assertNotNull(allOrdersList, "All orders list should be not null.");
//         assertEquals(2, allOrdersList.size(), "Order List should  have 2 orders.");
//      }
   
}
