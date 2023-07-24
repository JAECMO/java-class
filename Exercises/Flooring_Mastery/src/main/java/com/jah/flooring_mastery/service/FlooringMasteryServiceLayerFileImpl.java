/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;


import com.jah.flooring_mastery.dao.FlooringMasteryFileCreationException;
import com.jah.flooring_mastery.dao.FlooringMasteryOrderDao;
import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDao;
import com.jah.flooring_mastery.dao.FlooringMasteryTaxDao;
import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Product;
import com.jah.flooring_mastery.dto.States;
import com.jah.flooring_mastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author drjal
 */
public class FlooringMasteryServiceLayerFileImpl implements FlooringMasteryServiceLayer {
    
    FlooringMasteryOrderDao orderDao;
    FlooringMasteryTaxDao taxDao;
    FlooringMasteryProductDao productDao;

    public FlooringMasteryServiceLayerFileImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryTaxDao taxDao, FlooringMasteryProductDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    @Override
    public int newOrderNumber() throws FlooringMasteryPersistenceException {
        int newOrderNumber=1;
        List<List<Order>> listOfOrdersList = orderDao.getAllOrders();
        List<Integer> orderNumberList = new ArrayList<>();
        for(int i = 0;i<listOfOrdersList.size();i++){
            for (int j = 0; j<listOfOrdersList.get(i).size();j++){
            orderNumberList.add(listOfOrdersList.get(i).get(j).getOrderNumber());
            }
        }
        
        try {
            newOrderNumber = Collections.max(orderNumberList) + 1;
        } catch (Exception e) {
            e.getMessage();
        }
        return newOrderNumber;
    }

    @Override
    public List<List<Order>> getAllOrders() throws FlooringMasteryPersistenceException {
        return orderDao.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        List<Order> orderList = orderDao.getOrdersByDate(date);
        
         if (orderList == null || orderList.isEmpty()){
            
            throw new FlooringMasteryPersistenceException(
                    "ERROR: there are no orders for this date");
        }
        return orderList;
    }

    @Override
    public Order createOrder(LocalDate date, Order order, Integer orderNumber) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
        return orderDao.createOrder(date, order, orderNumber);
    }
    

    @Override
    public void orderDateValidation(LocalDate date,LocalDate minDate, LocalDate maxDate) throws OrderDateValidationException {
       
        if ( date.isBefore(minDate) || date.isAfter(maxDate) ){
            throw new OrderDateValidationException(
                    "ERROR: order Date is not within the specified interval");
        }
    }

    @Override
    public void customerNameValidation(String name) throws CustomerNameValidationException {
        String pattern = "^[a-zA-Z0-9.,\\s]+$";

        if (name.isBlank() || name.isEmpty()) {
            throw new CustomerNameValidationException(
                    "ERROR: name cannot be blank");
        } else if (!Pattern.matches(pattern, name)) {
            throw new CustomerNameValidationException(
                    "ERROR: Only letters,numbers,commas and periods are allowed");
        }

    }

    @Override
    public void areaValidation(BigDecimal area) throws AreaValidationException {
        
        if (area.compareTo(new BigDecimal("100"))<0){
        throw new AreaValidationException(
                    "ERROR: area is lower than the Min order (100 sq ft)");
        }
    }

    @Override
    public List<Tax> getAllTax() throws FlooringMasteryPersistenceException {
        return taxDao.getAllTax();
    }

    @Override
    public Tax getTax(String stateName) throws StateValidationException,FlooringMasteryPersistenceException {
        
         String verifiedStateName = verifyStateName(stateName);
 
        Tax tax = taxDao.getTax(verifiedStateName);
        if (tax == null){
            
            throw new StateValidationException(
                    "ERROR: we don't serve this state");
        }
        return tax;
    }

    @Override
    public void stateValidation(String stateName) throws StateValidationException, FlooringMasteryPersistenceException {
        List<Tax> taxList = getAllTax();
        for (int i = 0; i < taxList.size(); i++) {
            if (taxList.get(i).getStateName().toUpperCase().equals(stateName.toUpperCase())) {
                break;
            } else if (i == taxList.size() - 1) {
                throw new StateValidationException(
                        "ERROR: we don't serve this state");
            }
        }//for
        
    }

    @Override
    public void productValidation(String productType) throws FlooringMasteryPersistenceException, ProductTypeValidationException {
        List<Product> productList = getAllProduct();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductType().toUpperCase().equals(productType.toUpperCase())) {
                break;
            } else if (i == productList.size() - 1) {
                throw new ProductTypeValidationException(
                        "ERROR: this product is not available");
            }
        }//for
    }

    @Override
    public List<Product> getAllProduct() throws FlooringMasteryPersistenceException {
        return productDao.getAllProduct();
    }

    @Override
    public Product getProduct(String productType) throws ProductTypeValidationException, FlooringMasteryPersistenceException {
    String verifiedProductName = verifyProductName(productType);
 
        Product product = productDao.getProduct(verifiedProductName);
        if (product == null){
            
            throw new ProductTypeValidationException(
                    "ERROR: This product doesn't exit ");
        }
        return product;
    }

    @Override
    public String verifyProductName(String productType) throws FlooringMasteryPersistenceException {
        return productDao.verifyProductName(productType);
    }

    @Override
    public String verifyStateName(String stateName) throws FlooringMasteryPersistenceException {
        return taxDao.verifyStateName(stateName);
    }

    @Override
    public BigDecimal materialCost(BigDecimal area, BigDecimal costPerSquareFoot) throws FlooringMasteryPersistenceException {
        BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return materialCost;
    }

    @Override
    public BigDecimal laborCost(BigDecimal area, BigDecimal laborPerSquareFoot) throws FlooringMasteryPersistenceException {
        BigDecimal laborCost = area.multiply(laborPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return laborCost;
    }

    @Override
    public BigDecimal taxValue(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) throws FlooringMasteryPersistenceException {
        BigDecimal taxValue = materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
        return taxValue;
    }

    @Override
    public BigDecimal total(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxValue) throws FlooringMasteryPersistenceException {
        BigDecimal total = materialCost.add(laborCost).add(taxValue).setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    @Override
    public boolean orderDateExistenceValidation(LocalDate date) throws OrderDateExistenceValidationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getListOrderNumberByDate(LocalDate date) throws FlooringMasteryPersistenceException {
            return orderDao.getListOrderNumberByDate(date);
        }

    @Override
    public Integer orderNumberValidation(int orderNumber, LocalDate date) throws OrderNumberValidationException, FlooringMasteryPersistenceException {
        List<Integer> orderNumberList = getListOrderNumberByDate(date);

        if (!orderNumberList.contains(orderNumber)) {

            throw new OrderNumberValidationException(
                    "ERROR: This order doesn't exist for this date ");

        }
        return orderNumber;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws OrderNumberValidationException, FlooringMasteryPersistenceException {
        Order order = orderDao.getOrder(orderNumber, date);
        
         if (order == null){
            
            throw new OrderNumberValidationException(
                    "ERROR: This order doesn't exist for this date ");
        }
        return order;
    }

    @Override
    public Order editOrder(int orderNumber, LocalDate date, Order orderEdited) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
            return orderDao.editOrder(orderNumber, date, orderEdited);
    }
   
    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
        return orderDao.removeOrder(orderNumber, date);
    }

    @Override
    public String findStatesNameFromAbb(String abb) throws StateExistenceValidationException {
        String stateName = null;
        for (States state : States.values()) {
            if (state.getAbbreviation().equalsIgnoreCase(abb)) {
                stateName = state.name();
            }
        }

        if (stateName == null) {

            throw new StateExistenceValidationException(
                    "ERROR: This states doesn't exist in the USA ");
        }
        return stateName;
    }

    @Override
    public String checkEditInputIfChanged(String input, String previousInput) {
        if (input.isEmpty() || input.isBlank()) {
            input = previousInput;
        }
        input = reverseUpdateCustomerName(input);
        return input;
    }

    @Override
    public List<Order> exportAllData() throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException {
        return orderDao.exportAllData();
    }

    @Override
    public String getExportLink() {
        return orderDao.getExportLink();
    }

    @Override
    public void checkOrderFolderPath() throws FolderPathValidationException {
        String folderPath = orderDao.checkOrderFolderPath();
        Path path = Paths.get(folderPath);
        
        if (!Files.exists(path)){
          throw new FolderPathValidationException(
                    "ERROR: You need to Create a Folder for your files "); 
        }
        
    }

    @Override
    public void checkBackupFolderPath() throws FolderPathValidationException {
         String folderPath = orderDao.checkBackupFolderPath();
        Path path = Paths.get(folderPath);
        
        if (!Files.exists(path)){
          throw new FolderPathValidationException(
                    "ERROR: You need to Create a Folder for your files "); 
        }
    }

    @Override
    public String updateCustomerName(String customerNameInput) {
        return customerNameInput.replace(",", "#");
    }

    @Override
    public String reverseUpdateCustomerName(String customerNameInput) {
         return customerNameInput.replace("#",",");
    }

    @Override
    public void customerNameValidationEdit(String name) throws CustomerNameValidationException {
         String pattern = "^[a-zA-Z0-9.,\\s]*$";// * is for allowing Empty String

     if (!Pattern.matches(pattern, name)) {
            throw new CustomerNameValidationException(
                    "ERROR: Only letters,numbers,commas and periods are allowed");
        }
    }

   

   
    
    
}
