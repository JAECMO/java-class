/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.service;

import com.jah.vending_machine.dao.VendingMachineAuditDao;
import com.jah.vending_machine.dao.VendingMachinePersistenceException;

/**
 *
 * @author drjal
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //do nothing . . .
    }
    
}
