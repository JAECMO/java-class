/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery;

import com.jah.flooring_mastery.controller.FlooringMasteryController;
import com.jah.flooring_mastery.dao.FlooringMasteryFileCreationException;
import com.jah.flooring_mastery.dao.FlooringMasteryOrderDao;
import com.jah.flooring_mastery.dao.FlooringMasteryOrderDaoFileImpl;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDao;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDaoFileImpl;
import com.jah.flooring_mastery.dao.FlooringMasteryTaxDao;
import com.jah.flooring_mastery.dao.FlooringMasteryTaxDaoFileImpl;
import com.jah.flooring_mastery.service.AreaValidationException;
import com.jah.flooring_mastery.service.CustomerNameValidationException;
import com.jah.flooring_mastery.service.FlooringMasteryServiceLayer;
import com.jah.flooring_mastery.service.FlooringMasteryServiceLayerFileImpl;
import com.jah.flooring_mastery.service.FolderPathValidationException;
import com.jah.flooring_mastery.service.OrderDateValidationException;
import com.jah.flooring_mastery.service.OrderNumberValidationException;
import com.jah.flooring_mastery.service.ProductTypeValidationException;
import com.jah.flooring_mastery.service.StateExistenceValidationException;
import com.jah.flooring_mastery.service.StateValidationException;
import com.jah.flooring_mastery.ui.FlooringMasteryView;
import com.jah.flooring_mastery.ui.UserIO;
import com.jah.flooring_mastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author drjal
 */
public class App {
     public static void main(String[] args) throws OrderDateValidationException, CustomerNameValidationException, AreaValidationException, StateValidationException, ProductTypeValidationException, OrderNumberValidationException, StateExistenceValidationException, NumberFormatException, FlooringMasteryFileCreationException, FolderPathValidationException  {
  
         ApplicationContext ctx
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        FlooringMasteryController controller
                = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();


//        // Instantiate the UserIO implementation
//        UserIO myIo = new UserIOConsoleImpl();
//        // Instantiate the View and wire the UserIO implementation into it
//        FlooringMasteryView myView = new FlooringMasteryView(myIo);
//        // Instantiate the DAO
//        FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoFileImpl();
//        FlooringMasteryTaxDao taxDao = new FlooringMasteryTaxDaoFileImpl();
//        FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoFileImpl();
//        // Instantiate the Audit DAO
//        FlooringMasteryAuditDao myAuditDao = new FlooringMasteryAuditDaoFileImpl();
//        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerFileImpl(orderDao,taxDao,productDao, myAuditDao);
//        // Instantiate the Controller and wire the Service Layer into it
//        FlooringMasteryController controller = new FlooringMasteryController(myService, myView);
//        // Kick off the Controller
//        controller.run();
     }
    
}
