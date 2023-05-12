/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.addressbook.controller;

import com.jah.addressbook.dao.AddressBookDao;
import com.jah.addressbook.dao.AddressBookDaoException;
import com.jah.addressbook.dto.Address;
import com.jah.addressbook.ui.AddressBookView;
import java.util.List;

/**
 *
 * @author drjal
 */
public class AddressBookController {
    
     private AddressBookView view;
    private AddressBookDao dao;
    
    public AddressBookController(AddressBookDao dao, AddressBookView view) {
    this.dao = dao;
    this.view = view;
}

   public void run() {
    boolean keepGoing = true;
    int menuSelection = 0;
    try {
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    addAddress();
                    break;
                case 2:
                    removeAddress();
                    break;
                case 3:
                    viewAddress();
                    break;
                case 4:
                    addressCount();
                    break;
                case 5:
                    listAddresses();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }//while
        exitMessage();
    } catch (AddressBookDaoException e) {
        view.displayErrorMessage(e.getMessage());
    }//catch
}//run()
   
   private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void addAddress() throws AddressBookDaoException {
        view.displayCreateAddressBanner();
        Address newAddress = view.getNewAddressInfo();
        dao.addAddress(newAddress.getLastName(), newAddress);
        view.displayCreateSuccessBanner();
    }
   
    private void removeAddress() throws AddressBookDaoException {
        view.displayRemoveAddressBanner();
        String lastName = view.getLastName();
        Address removedAddress = dao.removeAddress(lastName);
        view.displayRemoveResult(removedAddress);
    }
    
    private void viewAddress() throws AddressBookDaoException {
    view.displayDisplayAddressBanner();
    String lastName = view.getLastName();
    Address address = dao.getAddress(lastName);
    view.displayAddress(address);
}
    
    private void addressCount() throws AddressBookDaoException {
    view.displayAddressCountBanner();
    int addressCount = dao.getAddressCount();
    view.displayAddressCountResult(addressCount);
}
   
    private void listAddresses() throws AddressBookDaoException {
    view.displayDisplayAllBanner();
    List<Address> addressList = dao.getAllAddresses();
    view.displayAddressList(addressList);
}
    
    
   private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
    
}
