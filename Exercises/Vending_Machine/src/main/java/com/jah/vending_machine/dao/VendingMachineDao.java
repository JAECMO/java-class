/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dao;

import com.jah.vending_machine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface VendingMachineDao {
    
    List<Item> getAllItems()
     throws VendingMachinePersistenceException;
    
    Item getItem(String name)
     throws VendingMachinePersistenceException;
    
    Item editItem(String name,Item item)
            throws VendingMachinePersistenceException;
     String verifyTitle(String name)
            throws VendingMachinePersistenceException;
    
    
}
