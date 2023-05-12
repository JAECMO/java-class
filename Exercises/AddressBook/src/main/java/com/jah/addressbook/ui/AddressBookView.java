/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.addressbook.ui;

import com.jah.addressbook.dto.Address;
import java.util.List;

/**
 *
 * @author drjal
 */
public class AddressBookView {
    
    private UserIO io;
    
    public AddressBookView(UserIO io) {
    this.io = io;
}

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add Address");
        io.print("2. Delete Address");
        io.print("3. Find Address");
        io.print("4. List Address Count");
        io.print("5. List All Addresses");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public Address getNewAddressInfo() {
        
        String firstName = io.readString("Please enter First Name:");
        String lastName = io.readString("Please enter Last Name:");
        String streetAddress = io.readString("Please Enter Street Address:");
        String city = io.readString("Please Enter City:");
        String state = io.readString("Please Enter State:");
        int zipCode = io.readInt("Please Enter Zip Code:");
        
        Address currentAddress = new Address(lastName);
        currentAddress.setFirstName(firstName);
        currentAddress.setStreetAddress(streetAddress);
        currentAddress.setCity(city);
        currentAddress.setState(state);
        currentAddress.setZipCode(zipCode);
        
        return currentAddress;
    }
    
    
    public String getLastName() {
        return io.readString("Please enter the last name associated with the address.");
    }
    
    public void displayDisplayAddressBanner() {
        io.print("=== Display Address ===");
    }
    
     public void displayAddress(Address address) {
        if (address != null) {
            io.print(address.getFirstName() + " " + address.getLastName());
            io.print(address.getStreetAddress());
            io.print(address.getCity() + ", " + address.getState() + ", " + address.getZipCode());
            io.print("");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }
     
     public void displayDisplayAllBanner() {
        io.print("=== Display All Addresses ===");
    }
     
      public void displayAddressList(List<Address> addressList) {
        for (Address currentAddress : addressList) {
            String adressInfo = String.format("%s %s" +"\n%s" + "\n%s, %s, %s\n",
                    currentAddress.getFirstName(),
                    currentAddress.getLastName(),
                    currentAddress.getStreetAddress(),
                    currentAddress.getCity(),
                    currentAddress.getState(),
                    currentAddress.getZipCode());
            io.print(adressInfo);
        }
        io.readString("Please hit enter to continue.");
    }
     
       public void displayAddressCountBanner() {
        io.print("=== Address Count ===");
    }
       
       public void displayAddressCountResult(int addressCount) {
        io.print("There are " + addressCount + " addresses in the book.\n");
    }
    
    public void displayCreateAddressBanner() {
        io.print("=== Create Address ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "Address successfully created.  Please hit enter to continue");
    }
    
     public void displayRemoveAddressBanner() {
        io.print("=== Remove Address ===");
    }

    public void displayRemoveResult(Address addressRecord) {
        if (addressRecord != null) {
            io.print("Address successfully removed.");
        } else {
            io.print("No such address.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
     public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
