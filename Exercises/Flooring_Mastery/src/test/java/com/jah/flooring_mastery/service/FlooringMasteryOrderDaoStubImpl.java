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
import com.jah.flooring_mastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author drjal
 */
public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {
    private final String ORDER_FILES_FOLDER_TEST;
    private final String BACKUP_FILE_FOLDER_TEST;

    public FlooringMasteryOrderDaoStubImpl() {
        ORDER_FILES_FOLDER_TEST = "SampleFileDataTest/Orders/";
        BACKUP_FILE_FOLDER_TEST = "SampleFileDataTest/Backup/";
    }

    public FlooringMasteryOrderDaoStubImpl(String pathFolderOrder, String pathFileBackUp) {
        ORDER_FILES_FOLDER_TEST = pathFolderOrder;
        BACKUP_FILE_FOLDER_TEST= pathFileBackUp;
    }

    public static final String DELIMITER = ",";

    private Map<LocalDate, List<Order>> orders = new HashMap<>();

    @Override
    public List<List<Order>> getAllOrders() throws FlooringMasteryPersistenceException {
        loadOrdersList();
        return new ArrayList(orders.values());
    }

    @Override
    public List<Order> exportAllData() throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {

        List<List<Order>> allOrders = getAllOrders();
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            for (int j = 0; j < allOrders.get(i).size(); j++) {
                orderList.add(allOrders.get(i).get(j));
            }
        }

        writeAllOrdersList(orderList);

        return orderList;
    }
    
    @Override
    public String getExportLink() {
        return BACKUP_FILE_FOLDER_TEST+"DataExport.txt";
    }

    @Override
    public String checkOrderFolderPath() {
        String folderPath = new FlooringMasteryOrderDaoStubImpl().ORDER_FILES_FOLDER_TEST;
        return folderPath;
    }
    
    @Override
    public String checkBackupFolderPath() {
        String folderPath = new FlooringMasteryOrderDaoStubImpl().BACKUP_FILE_FOLDER_TEST;
        return folderPath;
    }
   

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        loadOrdersList();
        if (orders.containsKey(date)) {
            List<Order> ordersList = new ArrayList(orders.get(date));
            return ordersList;
        } else {
            return null;
        }
    }

    @Override
    public List<Integer> getListOrderNumberByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        List<Order> orderList = getOrdersByDate(date);
        List<Integer> orderNumberList = orderList.stream().map((o) -> o.getOrderNumber()).collect(Collectors.toList());
        return orderNumberList;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        List<Order> orderList = getOrdersByDate(date);
        Optional<Order> orderOp = orderList.stream().filter((o) -> o.getOrderNumber() == orderNumber).findFirst();

        Order order = null;
        if (orderOp.isPresent()) {
            order = orderOp.get();
        }

        return order;
    }

    @Override
    public Order editOrder(int orderNumber, LocalDate date, Order orderEdited) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
        Order order = getOrder(orderNumber, date);
        List<Order> orderList = getOrdersByDate(date);
        int indexOrder = orderList.indexOf(order);
        orderList.set(indexOrder, orderEdited);
        orders.replace(date, orderList);

        writeOrdersList(date);

        return orderEdited;
    }

    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
        Order removedOrder = getOrder(orderNumber, date);
        List<Order> orderList = getOrdersByDate(date);
        int indexOrder = orderList.indexOf(removedOrder);
        orderList.remove(indexOrder);
        orders.replace(date, orderList);

        writeOrdersList(date);

        return removedOrder;
    }

    @Override
    public Order createOrder(LocalDate date, Order order, Integer orderNumber) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {

        loadOrdersList();

        List<Order> orderList = new ArrayList<>();

        if (!orders.containsKey(date)) {
            orderList.add(order);
        } else {

            orderList = orders.get(date);
            orderList.add(order);
        }
        orders.put(date, orderList);

        writeOrdersList(date);

        return order;
    }

    private Order unmarshallOrder(LocalDate orderDate, String orderAsText) throws FlooringMasteryPersistenceException {

        String[] orderTokens = orderAsText.split(DELIMITER);

        try {
            int orderNumber = Integer.parseInt(orderTokens[0]);
            String customerName = orderTokens[1];
            String state = orderTokens[2];
            BigDecimal taxRate = new BigDecimal(orderTokens[3]);
            String productType = orderTokens[4];
            BigDecimal area = new BigDecimal(orderTokens[5]);
            BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[7]);
            BigDecimal materialCost = new BigDecimal(orderTokens[8]);
            BigDecimal laborCost = new BigDecimal(orderTokens[9]);
            BigDecimal tax = new BigDecimal(orderTokens[10]);
            BigDecimal Total = new BigDecimal(orderTokens[11]);

            return new Order(orderDate, orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, Total);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            // Handle the exception appropriately
            // For example, log an error message or perform error recovery

            throw new FlooringMasteryPersistenceException("Error in your text file!!", e); // Return null or throw an exception indicating the failure
        }
    }

    private void loadOrdersList() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        String folderPath = new FlooringMasteryOrderDaoStubImpl().ORDER_FILES_FOLDER_TEST;
        
        File[] files = findFilesinFolder();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName(); // Get the name of the file
                    // System.out.print(fileName);
                    try {
                        BufferedReader reader = new BufferedReader(
                                new FileReader(folderPath + fileName));

                        // Create Scanner for reading the file
                        scanner = new Scanner(reader);

                    } catch (FileNotFoundException e) {
                        throw new FlooringMasteryPersistenceException(
                                "-_- Could not load order list data into memory.", e);
                    }
                    //Skip the first line
                    try {
                        scanner.nextLine();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    String currentLine;
                    List<Order> currentOrder = new ArrayList<>();
                    LocalDate orderDate = splitDate(fileName);

                    while (scanner.hasNextLine()) {
                        currentLine = scanner.nextLine();
                        currentOrder.add(unmarshallOrder(orderDate, currentLine));

                    }
                    orders.put(orderDate, currentOrder);
                    // close scanner
                    scanner.close();
                }
            }
        }

    }

    private String marshallOrder(Order anOrder) {

        String orderAsText = anOrder.getOrderNumber() + DELIMITER;

        orderAsText += anOrder.getCustomerName() + DELIMITER;

        orderAsText += anOrder.getState() + DELIMITER;

        orderAsText += anOrder.getTaxRate() + DELIMITER;

        orderAsText += anOrder.getProductType() + DELIMITER;

        orderAsText += anOrder.getArea() + DELIMITER;

        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;

        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;

        orderAsText += anOrder.getMaterialCost() + DELIMITER;

        orderAsText += anOrder.getLaborCost() + DELIMITER;

        orderAsText += anOrder.getTax() + DELIMITER;

        orderAsText += anOrder.getTotal();

        return orderAsText;
    }

    private void writeOrdersList(LocalDate date) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {

        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String filePath = ORDER_FILES_FOLDER_TEST + "Orders_" + formattedDate + ".txt";

        PrintWriter out;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                out = new PrintWriter(new FileWriter(filePath));
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new FlooringMasteryFileCreationException(
                            "Failed to create the file.", e);
                }
                out = new PrintWriter(new FileWriter(filePath));
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }

        String orderAsText;
        List<Order> orderList = orders.get(date);
        orderAsText = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        out.println(orderAsText);
        for (Order currentOrder : orderList) {
            // turn an Order into a String
            orderAsText = marshallOrder(currentOrder);

            out.println(orderAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }

    private String marshallAllOrder(Order anOrder) {

        String orderAsText = anOrder.getOrderNumber() + DELIMITER;

        orderAsText += anOrder.getCustomerName() + DELIMITER;

        orderAsText += anOrder.getState() + DELIMITER;

        orderAsText += anOrder.getTaxRate() + DELIMITER;

        orderAsText += anOrder.getProductType() + DELIMITER;

        orderAsText += anOrder.getArea() + DELIMITER;

        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;

        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;

        orderAsText += anOrder.getMaterialCost() + DELIMITER;

        orderAsText += anOrder.getLaborCost() + DELIMITER;

        orderAsText += anOrder.getTax() + DELIMITER;

        orderAsText += anOrder.getTotal() + DELIMITER;

        orderAsText += anOrder.getOrderDate();

        return orderAsText;
    }

    private void writeAllOrdersList(List<Order> orderList) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {

        String filePath = BACKUP_FILE_FOLDER_TEST+"DataExport.txt";

        PrintWriter out;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                out = new PrintWriter(new FileWriter(filePath));
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new FlooringMasteryFileCreationException(
                            "Failed to create the file.", e);
                }
                out = new PrintWriter(new FileWriter(filePath));
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }

        String orderAsText;
        orderAsText = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate";
        out.println(orderAsText);
        for (Order currentOrder : orderList) {
            // turn an Order into a String
            orderAsText = marshallAllOrder(currentOrder);

            out.println(orderAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }

    @Override
    public File[] findFilesinFolder() {
        String folderPath = new FlooringMasteryOrderDaoStubImpl().ORDER_FILES_FOLDER_TEST;
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        return files;
    }

    private LocalDate splitDate(String fileName) {
        String[] parts = fileName.split("[^\\d]+");
        LocalDate date = LocalDate.now();

        if (parts.length > 1) {
            String numbers = parts[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            date = LocalDate.parse(numbers, formatter);
        }
        return date;
    }

}
