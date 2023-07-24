/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

import com.jah.flooring_mastery.dto.Order;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface FlooringMasteryOrderDao {
    
    List<List<Order>> getAllOrders()
     throws FlooringMasteryPersistenceException;
    
    List<Order> getOrdersByDate(LocalDate date)
     throws FlooringMasteryPersistenceException;
    
    Order getOrder(int orderNumber, LocalDate date)
            throws FlooringMasteryPersistenceException;
    
    Order editOrder(int orderNumber, LocalDate date, Order orderEdited)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    Order createOrder(LocalDate date, Order order, Integer orderNumber)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    File[] findFilesinFolder()
            throws FlooringMasteryPersistenceException;
    
    List<Integer> getListOrderNumberByDate(LocalDate date)
            throws FlooringMasteryPersistenceException;
    
    Order removeOrder(int orderNumber, LocalDate date)
            throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    List<Order> exportAllData()
             throws FlooringMasteryPersistenceException,FlooringMasteryFileCreationException;
    
    String getExportLink();
    
    String checkOrderFolderPath();
    
    String checkBackupFolderPath();
            
}