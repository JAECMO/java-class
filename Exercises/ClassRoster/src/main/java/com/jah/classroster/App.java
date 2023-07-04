/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.classroster;

import com.jah.classroster.controller.ClassRosterController;
import com.jah.classroster.dao.ClassRosterAuditDao;
import com.jah.classroster.dao.ClassRosterAuditDaoFileImpl;
import com.jah.classroster.dao.ClassRosterDao;
import com.jah.classroster.dao.ClassRosterDaoFileImpl;
import com.jah.classroster.ui.ClassRosterView;
import com.jah.classroster.ui.UserIO;
import com.jah.classroster.ui.UserIOConsoleImpl;
import com.sg.classroster.service.ClassRosterServiceLayer;
import com.sg.classroster.service.ClassRosterServiceLayerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author drjal
 */
public class App {
      public static void main(String[] args) {
//          // Instantiate the UserIO implementation
//          UserIO myIo = new UserIOConsoleImpl();
//          // Instantiate the View and wire the UserIO implementation into it
//          ClassRosterView myView = new ClassRosterView(myIo);
//          // Instantiate the DAO
//          ClassRosterDao myDao = new ClassRosterDaoFileImpl();
//          // Instantiate the Audit DAO
//          ClassRosterAuditDao myAuditDao = new ClassRosterAuditDaoFileImpl();
//          // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//          ClassRosterServiceLayer myService = new ClassRosterServiceLayerImpl(myDao, myAuditDao);
//          // Instantiate the Controller and wire the Service Layer into it
//          ClassRosterController controller = new ClassRosterController(myService, myView);
//          // Kick off the Controller
//          controller.run();


          ApplicationContext ctx
                  = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
          ClassRosterController controller
                  = ctx.getBean("controller", ClassRosterController.class);
          controller.run();
          
          
//           ApplicationContext appContext
//                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//
//        BookController controller = appContext.getBean("controller", BookController.class);
    }
    
}
