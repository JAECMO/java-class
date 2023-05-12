/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.addressbook.dao;

import com.jah.addressbook.dto.Address;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class AddressBookDaoFileImpl implements AddressBookDao {
    
    public static final String ADDRESS_LIST_FILE = "addressbooklist.txt";
    public static final String DELIMITER = "::";
    
    private Map<String, Address> addresses = new HashMap<>();

    @Override
    public Address addAddress(String lastName, Address address ) throws AddressBookDaoException {
        loadAddressList();
        Address newAddress = addresses.put(lastName, address);
        writeAddressList();
        return newAddress;
    }
    
     @Override
    public Address removeAddress(String lastName)
            throws AddressBookDaoException {
        loadAddressList();
        Address removeAddress = addresses.remove(lastName);
        writeAddressList();
        return removeAddress;
    }
    
    @Override
    public Address getAddress(String lastName)
            throws AddressBookDaoException {
        loadAddressList();
        return addresses.get(lastName);
    }
    
    @Override
    public int getAddressCount() throws AddressBookDaoException {
        loadAddressList();
        return addresses.size();
    }

    @Override
    public List<Address> getAllAddresses()
            throws AddressBookDaoException {
        loadAddressList();
        return new ArrayList(addresses.values());
    }
    
    
     private Address unmarshallAddress(String addressAsText) {

        String[] addressTokens = addressAsText.split(DELIMITER);

        String lastName = addressTokens[0];

        Address addressFromFile = new Address(lastName);

        addressFromFile.setFirstName(addressTokens[1]);

        addressFromFile.setStreetAddress(addressTokens[2]);

        addressFromFile.setCity(addressTokens[3]);

        addressFromFile.setState(addressTokens[4]);

        addressFromFile.setZipCode(Integer.parseInt(addressTokens[5]));

        return addressFromFile;
    }

    private void loadAddressList() throws AddressBookDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ADDRESS_LIST_FILE)));
        } catch (FileNotFoundException e) {
            throw new AddressBookDaoException(
                    "-_- Could not load address list data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentAddress holds the most recent address unmarshalled
        Address currentAddress;

        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into an Address
            currentAddress = unmarshallAddress(currentLine);

            addresses.put(currentAddress.getLastName(), currentAddress);
        }
        // close scanner
        scanner.close();
    }

    private String marshallAddress(Address anAddress) {

        String addressAsText = anAddress.getLastName() + DELIMITER;

        addressAsText += anAddress.getFirstName() + DELIMITER;

        addressAsText += anAddress.getStreetAddress() + DELIMITER;

        addressAsText += anAddress.getCity() + DELIMITER;

        addressAsText += anAddress.getState() + DELIMITER;

        addressAsText += anAddress.getZipCode();

        return addressAsText;
    }

    private void writeAddressList() throws AddressBookDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ADDRESS_LIST_FILE));
        } catch (IOException e) {
            throw new AddressBookDaoException(
                    "Could not save address data.", e);
        }

        String addressAsText;
        List<Address> addressList = this.getAllAddresses();
        for (Address currentAddress : addressList) {

            addressAsText = marshallAddress(currentAddress);

            out.println(addressAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }

   
}
