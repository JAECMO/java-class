/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine;

import com.jah.vending_machine.controller.VendingMachineController;
import com.jah.vending_machine.dao.VendingMachineAuditDao;
import com.jah.vending_machine.dao.VendingMachineAuditDaoFileImpl;
import com.jah.vending_machine.dao.VendingMachineDao;
import com.jah.vending_machine.dao.VendingMachineDaoFileImpl;
import com.jah.vending_machine.dto.Change;
import com.jah.vending_machine.service.InsufficientFundsException;
import com.jah.vending_machine.service.NoItemInventoryException;
import com.jah.vending_machine.service.VendingMachineServiceLayer;
import com.jah.vending_machine.service.VendingMachineServiceLayerImpl;
import com.jah.vending_machine.ui.UserIO;
import com.jah.vending_machine.ui.UserIOConsoleImpl;
import com.jah.vending_machine.ui.VendingMachineView;
import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author drjal
 */
public class App {

    public static void main(String[] args) throws InsufficientFundsException, NoItemInventoryException {
//        // Instantiate the UserIO implementation
//        UserIO myIo = new UserIOConsoleImpl();
//        // Instantiate the View and wire the UserIO implementation into it
//        VendingMachineView myView = new VendingMachineView(myIo);
//        // Instantiate the DAO
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//        // Instantiate the Audit DAO
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
//        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
//        // Instantiate the Controller and wire the Service Layer into it
//        VendingMachineController controller = new VendingMachineController(myService, myView);
//        // Kick off the Controller
//        controller.run();

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        VendingMachineController controller
                = ctx.getBean("controller", VendingMachineController.class);
        controller.run();

//BigDecimal itemCost = new BigDecimal("137.65");
//System.out.println(Change.getQuarter(itemCost));
//System.out.println(Change.getDime(itemCost));
//System.out.println(Change.getNickel(itemCost));
//System.out.println(Change.getPenny(itemCost));
    }
    
}
