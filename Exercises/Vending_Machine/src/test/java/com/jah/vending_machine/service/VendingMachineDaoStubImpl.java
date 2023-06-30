/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.service;

import com.jah.vending_machine.dao.VendingMachineDao;
import com.jah.vending_machine.dao.VendingMachineDaoFileImpl;
import com.jah.vending_machine.dao.VendingMachinePersistenceException;
import com.jah.vending_machine.dto.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author drjal
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    public Item onlyItem;

    public VendingMachineDaoStubImpl(Item testItem){
         this.onlyItem = testItem;
     }

    VendingMachineDaoStubImpl() {
        onlyItem = new Item("Chips");
        onlyItem.setCost(new BigDecimal("1.25"));
        onlyItem.setInventory(1);
    }


    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
       
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {

        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }
    

    @Override
    public Item editItem(String name, Item item) throws VendingMachinePersistenceException {
  
        if (name.equals(item.getName())) {
            return item;
        } else {
            return null;
        }
    }
    
    @Override
    public String verifyTitle(String name) throws VendingMachinePersistenceException {
      return name;
    }
    
}
