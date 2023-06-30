/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.service;

import com.jah.vending_machine.dao.VendingMachineAuditDao;
import com.jah.vending_machine.dao.VendingMachineDao;
import com.jah.vending_machine.dao.VendingMachineDaoFileImpl;
import com.jah.vending_machine.dao.VendingMachinePersistenceException;
import com.jah.vending_machine.dto.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class VendingMachineServiceLayerImplTest {
    VendingMachineDao testDao;
    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }

    
    @Test
public void testEnoughFunds() throws Exception {
    // ARRANGE
        Item item = new Item("Candy");
        item.setCost(new BigDecimal("2.99"));
        item.setInventory(2);
        BigDecimal amount = new BigDecimal("2.99");
    // ACT    
    try {
        BigDecimal moneyLeft = service.moneyLeft(item, amount);
        service.checkIfSufficientFunds(moneyLeft);
    } catch (VendingMachinePersistenceException
            | InsufficientFundsException e) {
    // ASSERT
        fail("Amount was enough. No exception should have been thrown ");
    }
}

    @Test
    public void testInsufficientFunds() throws Exception {
        // ARRANGE
        Item item = new Item("Candy");
        item.setCost(new BigDecimal("2.99"));
        item.setInventory(2);
        BigDecimal amount = new BigDecimal("1.99");
        // ACT    
        try {
            BigDecimal moneyLeft = service.moneyLeft(item, amount);
            service.checkIfSufficientFunds(moneyLeft);
            fail("Amount was NOT enough. Exception was not thrown ");
        } catch (VendingMachinePersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            return;

        }
    }
    
        @Test
    public void testNoInventory() throws Exception {
        // ARRANGE
        Item item = new Item("Candy");
        item.setCost(new BigDecimal("2.99"));
        item.setInventory(0);      
        // ACT    
        try {
            service.checkInventory(item);
            fail("Inventory was 0. Exception was not thrown ");
        }catch (NoItemInventoryException  e){
           return;
        }
        
    
    }
    
    @Test
    public void testBuyItemAndNoMoreInventory() throws Exception {
        // ARRANGE
        Item item = new Item("Candy");
        item.setCost(new BigDecimal("2.99"));
        item.setInventory(1);
        BigDecimal amount = new BigDecimal("6");

        try {
            service.editItemInventory("Candy", item);
            assertEquals(item.getInventory(), 0);
            service.checkInventory(item);
            service.checkIfSufficientFunds(amount);
            fail("Inventory was 0. Exception was not thrown ");
        } catch (NoItemInventoryException e) {
            return;
        } catch (InsufficientFundsException e) {
            fail("Amount was enough. No exception should have been thrown ");
        } catch (VendingMachinePersistenceException e) {
            fail("Incorrect exception was thrown.");
        }
    }
    
    @Test
    public void testGetAllITems() throws Exception {
        // ARRANGE
        Item testClone = new Item("Chips");
        testClone.setCost(new BigDecimal("1.25"));
        testClone.setInventory(1);

        // ACT & ASSERT
        assertEquals(1, service.getAllItems().size(),
                "Should only have one item.");
        assertNotNull(testClone);
        assertTrue(service.getAllItems().contains(testClone),
                "The one item should be Chips.");
    }
    
    @Test
    public void testGetItem() throws Exception {
        // ARRANGE
        Item testClone = new Item("Chips");
        testClone.setCost(new BigDecimal("1.25"));
        testClone.setInventory(1);

        // ACT & ASSERT
        

        Item shouldBeChips = service.getItem("Chips");
        assertNotNull(shouldBeChips, "Getting Chips should be not null.");
        assertEquals(testClone, shouldBeChips,
                "Item should be similar.");
    
        try {
            service.getItem("Candy");
            fail("Item does not exist.Exception was not thrown ");
        } catch (VendingMachinePersistenceException e) {
            return;
        }

    }
    @Test
    public void testEditItem() throws Exception {
        // ARRANGE
        Item testClone = new Item("Chips");
        testClone.setCost(new BigDecimal("1.25"));
        testClone.setInventory(1);

        // ACT & ASSERT
        Item shouldBeChips = service.editItemInventory("Chips", testClone);
        assertNotNull(shouldBeChips, "Editing Chips should be not null.");
        assertEquals(testClone, shouldBeChips, "Item edited from Chips should be Chips.");
    }
}
