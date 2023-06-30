/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.service;

import com.jah.vending_machine.dao.VendingMachineAuditDao;
import com.jah.vending_machine.dao.VendingMachineDao;
import com.jah.vending_machine.dao.VendingMachinePersistenceException;
import com.jah.vending_machine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drjal
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    
    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
      String verifiedName = verifyTitle(name);
 
        Item item = dao.getItem(verifiedName);
        if (item == null){
            
            throw new VendingMachinePersistenceException(
                    "ERROR: This item doesn't exit ");
        }
        return item;
    }

    @Override
    public void checkInventory(Item item) throws NoItemInventoryException {
        if (item.getInventory() == 0) {

            throw new NoItemInventoryException(
                    "ERROR: The item is no longer available");
        }

    }

    @Override
    public void checkIfSufficientFunds(BigDecimal moneyLeft) throws InsufficientFundsException {
        if ( moneyLeft.compareTo(BigDecimal.ZERO)<0){
            throw new InsufficientFundsException(
                    "ERROR: Insufficent Funds");
        }
    }

    @Override
    public BigDecimal moneyLeft(Item item, BigDecimal amount) throws VendingMachinePersistenceException {
        BigDecimal total = amount.subtract(item.getCost());
        
        return total;
    }

    @Override
    public Item editItemInventory(String name, Item item) throws VendingMachinePersistenceException {
        item.setInventory(item.getInventory()-1);
        Item updatedItem = dao.editItem(name, item);  
        auditDao.writeAuditEntry("Item " + item.getName() + " was bought.Inventory is now equal to "+item.getInventory());
        return updatedItem;
    }

    @Override
    public String verifyTitle(String name) throws VendingMachinePersistenceException {      
        return dao.verifyTitle(name);
    }
    
    
    
    
}
