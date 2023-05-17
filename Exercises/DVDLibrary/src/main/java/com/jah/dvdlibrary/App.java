/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.dvdlibrary;

import com.jah.dvdlibrary.controller.DVDLibraryController;
import com.jah.dvdlibrary.dao.DVDLibraryDao;
import com.jah.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.jah.dvdlibrary.ui.DVDView;
import com.jah.dvdlibrary.ui.UserIO;
import com.jah.dvdlibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author drjal
 */
public class App {
  public static void main(String[] args) {
    
    UserIO myIo = new UserIOConsoleImpl();
    DVDView myView = new DVDView(myIo);
    DVDLibraryDao myDao = new DVDLibraryDaoFileImpl();
    DVDLibraryController controller
            = new DVDLibraryController(myDao, myView);

    controller.run ();
    }
    
}
