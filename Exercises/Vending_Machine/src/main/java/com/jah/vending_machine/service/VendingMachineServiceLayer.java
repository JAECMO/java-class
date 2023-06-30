/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.service;

import com.jah.vending_machine.dao.VendingMachinePersistenceException;
import com.jah.vending_machine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface VendingMachineServiceLayer {
    
    List<Item> getAllItems()
     throws VendingMachinePersistenceException;
    
    Item getItem(String name)
     throws VendingMachinePersistenceException,InsufficientFundsException,NoItemInventoryException;
    
    Item editItemInventory(String name,Item item)
            throws VendingMachinePersistenceException;
    
    void checkInventory (Item item)
            throws NoItemInventoryException;
    
    void checkIfSufficientFunds(BigDecimal moneyLeft)
            throws InsufficientFundsException;
    
    BigDecimal moneyLeft(Item item, BigDecimal amount)
            throws VendingMachinePersistenceException;
    
    String verifyTitle(String name)
            throws VendingMachinePersistenceException;

    
}
