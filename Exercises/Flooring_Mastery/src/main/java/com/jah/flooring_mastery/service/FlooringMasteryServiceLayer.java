/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;

import com.jah.flooring_mastery.dao.FlooringMasteryFileCreationException;
import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Product;
import com.jah.flooring_mastery.dto.States;
import com.jah.flooring_mastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface FlooringMasteryServiceLayer {

    int newOrderNumber() throws FlooringMasteryPersistenceException;

    List<List<Order>> getAllOrders()
            throws FlooringMasteryPersistenceException;

    List<Order> getOrdersByDate(LocalDate date)
            throws FlooringMasteryPersistenceException;

    Order createOrder(LocalDate date, Order order, Integer orderNumber)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;

    void orderDateValidation(LocalDate date, LocalDate minDate, LocalDate maxDate)
            throws OrderDateValidationException;

    void customerNameValidation(String name)
            throws CustomerNameValidationException;
    
    void customerNameValidationEdit(String name)
            throws CustomerNameValidationException;

    void areaValidation(BigDecimal area)
            throws AreaValidationException;
    
     List<Tax> getAllTax()
     throws FlooringMasteryPersistenceException;
    
    Tax getTax(String stateName)
     throws StateValidationException,FlooringMasteryPersistenceException;
    
    void stateValidation(String stateName)
            throws FlooringMasteryPersistenceException,StateValidationException;
   
    void productValidation(String productType)
            throws FlooringMasteryPersistenceException,ProductTypeValidationException;

    List<Product> getAllProduct()
            throws FlooringMasteryPersistenceException;
    
    Product getProduct(String productType)
            throws ProductTypeValidationException, FlooringMasteryPersistenceException;

    String verifyProductName(String productType)
            throws FlooringMasteryPersistenceException;

    String verifyStateName(String stateName)
            throws FlooringMasteryPersistenceException;
  
    BigDecimal materialCost(BigDecimal area, BigDecimal costPerSquareFoot)
            throws FlooringMasteryPersistenceException;
    
    BigDecimal laborCost(BigDecimal area, BigDecimal laborPerSquareFoot)
            throws FlooringMasteryPersistenceException;
    
    BigDecimal taxValue(BigDecimal materialCost, BigDecimal laborCost,BigDecimal taxRate)
            throws FlooringMasteryPersistenceException;
    
    BigDecimal total(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxValue)
            throws FlooringMasteryPersistenceException;

    boolean orderDateExistenceValidation(LocalDate date)
            throws OrderDateExistenceValidationException;

    List<Integer> getListOrderNumberByDate(LocalDate date)
            throws FlooringMasteryPersistenceException;

    Integer orderNumberValidation(int orderNumber, LocalDate date)
            throws OrderNumberValidationException, FlooringMasteryPersistenceException;

    Order getOrder(int orderNumber, LocalDate date)
            throws OrderNumberValidationException, FlooringMasteryPersistenceException;

    Order editOrder(int orderNumber, LocalDate date, Order orderEdited)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;

    String findStatesNameFromAbb(String abb)
            throws StateExistenceValidationException;

    String checkEditInputIfChanged(String input, String previousInput);

    Order removeOrder(int orderNumber, LocalDate date)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    List<Order> exportAllData()
             throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    String getExportLink();
    
    void checkOrderFolderPath()
            throws FolderPathValidationException;
    
    void checkBackupFolderPath()
            throws FolderPathValidationException;
    
    String updateCustomerName(String customerNameInput);
    
    String reverseUpdateCustomerName(String customerNameInput);
         
           
}
