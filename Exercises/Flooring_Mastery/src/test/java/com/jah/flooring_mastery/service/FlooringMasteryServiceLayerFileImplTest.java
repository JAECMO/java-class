/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;

import com.jah.flooring_mastery.dao.FlooringMasteryFileCreationException;
import com.jah.flooring_mastery.dao.FlooringMasteryOrderDao;
import com.jah.flooring_mastery.dao.FlooringMasteryOrderDaoFileImpl;
import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDao;
import com.jah.flooring_mastery.dao.FlooringMasteryTaxDao;
import com.jah.flooring_mastery.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.platform.commons.function.Try.success;

/**
 *
 * @author drjal
 */
public class FlooringMasteryServiceLayerFileImplTest {

    private FlooringMasteryServiceLayer service;
    private FlooringMasteryServiceLayer service2;
    private FlooringMasteryServiceLayer service3;

    public FlooringMasteryServiceLayerFileImplTest() {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoStubImpl();
        FlooringMasteryOrderDao orderDao2 = new FlooringMasteryOrderDaoStubImpl("SampleFileDataTest2/Orders/", "SampleFileDataTest2/Backup/");
        FlooringMasteryOrderDao orderDao3 = new FlooringMasteryOrderDaoStubImpl("SampleFileDataTest3/Orders/", "SampleFileDataTest3/Backup/");
        FlooringMasteryTaxDao taxDao = new FlooringMasteryTaxDaoStubImpl();
        FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoStubImpl();

        service = new FlooringMasteryServiceLayerFileImpl(orderDao, taxDao, productDao);
        service2 = new FlooringMasteryServiceLayerFileImpl(orderDao2, taxDao, productDao);
        service3 = new FlooringMasteryServiceLayerFileImpl(orderDao3, taxDao, productDao);
    }

    @Test
    public void newOrderNumberAddGetOrder() throws Exception {

        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();
        }
        String dateString = "2023-07-28"; // Replace this with your date string
        String dateString2 = "2023-07-30"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        LocalDate orderDate2 = LocalDate.parse(dateString2, formatter);

        int orderNumber1 = 1;
        int orderNumber2 = 7;
        int orderNumber3 = 2;
        int orderNumberFake = 12;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2 = new Order(orderDate, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));
        Order order3 = new Order(orderDate2, orderNumber3, "Kyle  Boomer", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        service.createOrder(orderDate, order1, orderNumber1);
        service.createOrder(orderDate, order2, orderNumber2);
        service.createOrder(orderDate2, order3, orderNumber3);

        int newOrderNumber = service.newOrderNumber();

        assertEquals(newOrderNumber, 8, "The new Order number should be 8.");

        Order order1Clone = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2Clone = new Order(orderDate, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));
        Order order3Clone = new Order(orderDate2, orderNumber3, "Kyle  Boomer", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        // ACT & ASSERT
        Order shouldBeOrder1 = service.getOrder(orderNumber1, orderDate);
        assertNotNull(shouldBeOrder1, "Getting order1 should be not null.");
        assertEquals(order1Clone, shouldBeOrder1,
                "Orders should be similar.");

        Order shouldBeOrder2 = service.getOrder(orderNumber2, orderDate);
        assertNotNull(shouldBeOrder2, "Getting order2 should be not null.");
        assertEquals(order2Clone, shouldBeOrder2,
                "Orders should be similar.");

        Order shouldBeOrder3 = service.getOrder(orderNumber3, orderDate2);
        assertNotNull(shouldBeOrder3, "Getting order3 should be not null.");
        assertEquals(order3Clone, shouldBeOrder3,
                "Orders should be similar.");

        try {
            service.getOrder(orderNumberFake, orderDate);
            fail("Order does not exist.Exception was not thrown ");
        } catch (FlooringMasteryPersistenceException | OrderNumberValidationException e) {

        }

    }

    @Test
    public void exportAndGetAllOrders() throws Exception {

        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();
        }

        String dateString = "2023-07-28"; // Replace this with your date string
        String dateString2 = "2023-07-30"; // Replace this with your date string
        //  String dateStringFake = "2023-08-01"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        LocalDate orderDate2 = LocalDate.parse(dateString2, formatter);

        int orderNumber1 = 1;
        int orderNumber2 = 7;
        int orderNumber3 = 2;
        //   int orderNumberFake = 12;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2 = new Order(orderDate, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));
        Order order3 = new Order(orderDate2, orderNumber3, "Kyle  Boomer", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        service.createOrder(orderDate, order1, orderNumber1);
        service.createOrder(orderDate, order2, orderNumber2);
        service.createOrder(orderDate2, order3, orderNumber3);

        List<Order> allOrdersList = service.exportAllData();

        // First check the general contents of the list
        assertNotNull(allOrdersList, "All orders list should be not null.");
        assertEquals(3, allOrdersList.size(), "All Orders List should  have 3 orders.");

        // Then the specifics
        assertTrue(allOrdersList.contains(order1),
                "The list of orders should include order1.");
        assertTrue(allOrdersList.contains(order2),
                "The list of orders should include order2.");
        assertTrue(allOrdersList.contains(order3),
                "The list of orders should include order3.");

    }

    @Test
    public void getOrdersByDateOrderDateValidation() throws Exception {

        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();
        }

        String dateString = "2023-07-28"; // Replace this with your date string
        String dateString2 = "2023-07-30"; // Replace this with your date string
        String dateString3 = "2023-08-15"; // Replace this with your date string
        String dateStringMin = "2023-07-29"; // Replace this with your date string
        String dateStringMax = "2023-08-29"; // Replace this with your date string
        //  String dateStringFake = "2023-08-01"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        LocalDate orderDate2 = LocalDate.parse(dateString2, formatter);
        LocalDate orderDate3 = LocalDate.parse(dateString3, formatter);
        LocalDate orderDateMin = LocalDate.parse(dateStringMin, formatter);
        LocalDate orderDateMax = LocalDate.parse(dateStringMax, formatter);

        int orderNumber1 = 1;
        int orderNumber2 = 7;
        int orderNumber3 = 2;

        Order order1 = new Order(orderDate2, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));
        Order order2 = new Order(orderDate2, orderNumber2, "Wendy WasoWitch", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));
        Order order3 = new Order(orderDate3, orderNumber3, "Kyle  Boomer", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("136.2"), new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("476.70"), new BigDecimal("565.23"), new BigDecimal("260.48"), new BigDecimal("1302.41"));

        List<Order> orderListByDate = new ArrayList<>();
        try {
            service.orderDateValidation(orderDate, orderDateMin, orderDateMax);
            fail("Date was out of range. Exception was not thrown");
        } catch (OrderDateValidationException e) {

            try {
                service.orderDateValidation(orderDate2, orderDateMin, orderDateMax);
            } catch (OrderDateValidationException f) {
                fail("Date was in range. Exception was thrown");
                return;
            }

            try {
                service.createOrder(orderDate2, order1, orderNumber1);
                service.createOrder(orderDate2, order2, orderNumber2);
                service.createOrder(orderDate3, order3, orderNumber3);
                orderListByDate = service.getOrdersByDate(orderDate2);
            } catch (FlooringMasteryPersistenceException | FlooringMasteryFileCreationException f) {
                fail("Should not have thrown an Exception. All input are correct");
                return;
            }

            try {
                service.orderNumberValidation(orderNumber3, orderDate2);
                fail("Order number does not belong to this date.Exception should have been thrown.");
            } catch (OrderNumberValidationException g) {
                // First check the general contents of the list
                assertNotNull(orderListByDate, "All orders list should be not null.");
                assertEquals(2, orderListByDate.size(), "Order by Date List should  have 2 orders.");

                // Then the specifics
                assertTrue(orderListByDate.contains(order1),
                        "The list of orders should include order1.");
                assertTrue(orderListByDate.contains(order2),
                        "The list of orders should include order2.");
                assertFalse(orderListByDate.contains(order3),
                        "The list of orders should Not include order3.");

            }
        }

    }

    @Test
    public void customerNameValidation() throws Exception {

        String nameEmpty = "";
        String nameBlank = " ";
        String nameWrong = "#/kdkdk**";
        String nameCorrect = "W mm...,,, OP";

        try {
            service.customerNameValidation(nameEmpty);
            service.customerNameValidation(nameBlank);
            service.customerNameValidation(nameWrong);
            fail("Exception should have been thrown.");
        } catch (CustomerNameValidationException e) {
            try {
                service.customerNameValidation(nameCorrect);
            } catch (CustomerNameValidationException f) {
                fail("name is correct. Exception was thrown");
                return;
            }

            try {
                service.customerNameValidationEdit(nameEmpty);
                service.customerNameValidationEdit(nameBlank);
            } catch (CustomerNameValidationException g) {
                fail("Empty/Blank String are allowed");
            }

        }

    }

    @Test
    public void stateValidationAndNameVerification() throws Exception {

        String stateName = "teXas";
        String stateNameNA = "alasKA";
        String verifiedName = "";

        try {
            verifiedName = service.verifyStateName(stateName);
            service.stateValidation(verifiedName);
        } catch (StateValidationException | FlooringMasteryPersistenceException e) {
            fail("State name is valid. Exception was thrown");
            return;
        }

        try {
            verifiedName = service.verifyStateName(stateNameNA);
            service.stateValidation(verifiedName);
            fail("State name is not valid. Exception was not thrown");
        } catch (StateValidationException | FlooringMasteryPersistenceException e) {
        }

    }

    @Test
    public void productValidationAndNameVerification() throws Exception {

        String productName = "wOOd";
        String productNameNA = "stOne";
        String verifiedName = "";

        try {
            verifiedName = service.verifyProductName(productName);
            service.productValidation(verifiedName);
        } catch (ProductTypeValidationException | FlooringMasteryPersistenceException e) {
            fail("Product type is valid. Exception was thrown");
            return;
        }

        try {
            verifiedName = service.verifyStateName(productNameNA);
            service.productValidation(verifiedName);
            fail("Product type is not valid. Exception was not thrown");
        } catch (ProductTypeValidationException | FlooringMasteryPersistenceException e) {
        }

    }

    @Test
    public void areaValidation() throws Exception {
        //AreaValidationException

        BigDecimal area = new BigDecimal("66");

        try {
            service.areaValidation(area);
            fail("Area is lower than 100 sq ft. Exception was not thrown");
        } catch (AreaValidationException e) {
        }

    }

    @Test
    public void wrongFormatOrderFileException() throws Exception {
        //Create our method test inputs

        String orderTestFolder = "SampleFileDataTest2/Orders/";
        String backupTestFolder = "SampleFileDataTest2/Backup/";

        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();

        }

        String dateString = "2023-08-21";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        String fileName = "Orders_08212023.txt";
        //Order number W
        String wrongFormat = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total" + "\n" + "W,Ju# kk.,CA,25.00,Tile,136.2,3.50,4.15,476.70,565.23,260.48,1302.41";
        String filePath = orderTestFolder + fileName;

        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(wrongFormat);

        try {
            service2.getOrdersByDate(orderDate);
            fail("Execption Should have been thrown.File format is wrong.");
        } catch (FlooringMasteryPersistenceException e) {

        } finally {
            fileWriter.close(); // Ensure the FileWriter is closed regardless of exception or not
            directory = new File(orderTestFolder);
            files = directory.listFiles();
            for (File file : files) {
                file.delete(); // Delete the files in the orderTestFolder
            }
            directoryBackUp = new File(backupTestFolder);
            filesB = directoryBackUp.listFiles();
            for (File file : filesB) {
                file.delete(); // Delete the files in the backupTestFolder
            }
        }
    }

    @Test
    public void noOrderFolderException() throws Exception {

        String dateString = "2023-07-28";
        int orderNumber1 = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        try {
            service3.createOrder(orderDate, order1, orderNumber1);
            fail("Exception was not thrown.Order Folder does not exist.");
        } catch (FlooringMasteryPersistenceException | FlooringMasteryFileCreationException e) {

        }

    }

    @Test
    public void removeOrder() throws Exception {
        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();
        }

        String dateString = "2023-07-28"; // Replace this with your date string
        String dateString2 = "2023-07-30"; // Replace this with your date string

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        LocalDate orderDate2 = LocalDate.parse(dateString2, formatter);


        int orderNumber1 = 1;
        int orderNumber2 = 7;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        Order order1Clone = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        service.createOrder(orderDate, order1, orderNumber1);

        // ARRANGE
        // ACT & ASSERT
        Order shouldBeorder1 = service.removeOrder(orderNumber1, orderDate);
        assertNotNull(shouldBeorder1, "Removing order1 should be not null.");
        assertEquals(order1Clone, shouldBeorder1, "Order removed with orderNumber1 and orderDate should be order1.");

        try {
            service.removeOrder(orderNumber2, orderDate2);
            fail("Order does not exist. Exception was not thrown.");
        } catch (NullPointerException e) {//Order should be null
        }
    }

    @Test
    public void editOrder() throws Exception {
        //Create our method test inputs
        String orderTestFolder = "SampleFileDataTest/Orders/";
        String backupTestFolder = "SampleFileDataTest/Backup/";
        //use the FileWriter to quickly blank the file
        File directory = new File(orderTestFolder);
        File directoryBackUp = new File(backupTestFolder);
        //deletes all the files in the orderTestFolder
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
        File[] filesB = directoryBackUp.listFiles();
        for (File file : filesB) {
            file.delete();
        }

        String dateString = "2023-07-28"; // Replace this with your date string

        //  String dateStringFake = "2023-08-01"; // Replace this with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);

        int orderNumber1 = 1;

        Order order1 = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        Order order1Clone = new Order(orderDate, orderNumber1, "John Smith", "TX", new BigDecimal("4.45"), "Carpet", new BigDecimal("100.00"), new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("225.00"), new BigDecimal("210.00"), new BigDecimal("19.36"), new BigDecimal("454.36"));

        service.createOrder(orderDate, order1, orderNumber1);

        // ARRANGE
        // ACT & ASSERT
        Order shouldBeorder1 = service.editOrder(orderNumber1, orderDate, order1Clone);
        assertNotNull(shouldBeorder1, "Editing order1 should be not null.");
        assertEquals(order1Clone, shouldBeorder1, "Order edited with orderNumber1 and orderDate should be order1.");

    }

}
