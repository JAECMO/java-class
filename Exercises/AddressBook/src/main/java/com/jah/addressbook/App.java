/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.addressbook;

import com.jah.addressbook.controller.AddressBookController;
import com.jah.addressbook.dao.AddressBookDao;
import com.jah.addressbook.dao.AddressBookDaoFileImpl;
import com.jah.addressbook.ui.AddressBookView;
import com.jah.addressbook.ui.UserIO;
import com.jah.addressbook.ui.UserIOConsoleImpl;

/**
 *
 * @author drjal
 */
public class App {
    public static void main(String[] args) {
    
    UserIO myIo = new UserIOConsoleImpl();
    AddressBookView myView = new AddressBookView(myIo);
    AddressBookDao myDao = new AddressBookDaoFileImpl();
    AddressBookController controller
            = new AddressBookController(myDao, myView);

    controller.run ();
    }
}
