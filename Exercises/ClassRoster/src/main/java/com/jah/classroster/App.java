/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.classroster;

import com.jah.classroster.controller.ClassRosterController;
import com.jah.classroster.dao.ClassRosterDao;
import com.jah.classroster.dao.ClassRosterDaoFileImpl;
import com.jah.classroster.ui.ClassRosterView;
import com.jah.classroster.ui.UserIO;
import com.jah.classroster.ui.UserIOConsoleImpl;

/**
 *
 * @author drjal
 */
public class App {
      public static void main(String[] args) {
          UserIO myIo = new UserIOConsoleImpl();
          ClassRosterView myView = new ClassRosterView(myIo);
          ClassRosterDao myDao = new ClassRosterDaoFileImpl();
          ClassRosterController controller
                  = new ClassRosterController(myDao, myView);
          controller.run();
    }
    
}
