/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dao;

import com.jah.vending_machine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author drjal
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    private final String ITEM_LIST_FILE;
    
    public VendingMachineDaoFileImpl() {
        ITEM_LIST_FILE = "item_list.txt";
    }

    public VendingMachineDaoFileImpl(String itemListTextFile) {
        ITEM_LIST_FILE = itemListTextFile;
    }
    
    public static final String DELIMITER = "::";

    private Map<String, Item> items = new HashMap<>();
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItemList();
        return new ArrayList(items.values());
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadItemList();
        return items.get(name);
    }

    @Override
    public Item editItem(String name, Item item) throws VendingMachinePersistenceException {
        loadItemList();
        Item updatedItem = items.replace(name, item);
        writeItemList();
        return updatedItem;
    }
    
//        private Item unmarshallItem(String itemAsText) {
//        String[] itemTokens = itemAsText.split(DELIMITER);
//        String name = itemTokens[0];
//        Item itemFromFile = new Item(name);
//        itemFromFile.setCost(new BigDecimal(itemTokens[1]));
//        itemFromFile.setInventory(Integer.parseInt(itemTokens[2]));
//        return itemFromFile;
//    }
    
    private Item unmarshallItem(String itemAsText) throws VendingMachinePersistenceException {
    String[] itemTokens = itemAsText.split(DELIMITER);

    try {
        String name = itemTokens[0];
        BigDecimal cost = new BigDecimal(itemTokens[1]);
        int inventory = Integer.parseInt(itemTokens[2]);

        return new Item(name, cost, inventory);
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // Handle the exception appropriately
        // For example, log an error message or perform error recovery

       throw new VendingMachinePersistenceException ("Error in your text file!!",e); // Return null or throw an exception indicating the failure
    }
}

    
    private void loadItemList() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_LIST_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load item list data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
    }
    
    private String marshallItem(Item anItem) {

        String itemAsText = anItem.getName() + DELIMITER;

        itemAsText += anItem.getCost() + DELIMITER;

        itemAsText += anItem.getInventory();

        return itemAsText;
    }

    private void writeItemList() throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_LIST_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn a Student into a String
            itemAsText = marshallItem(currentItem);

            out.println(itemAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }

    @Override
    public String verifyTitle(String name) throws VendingMachinePersistenceException {
       loadItemList();
        String str = name;

        for (String key : items.keySet()) {
            if (key.toLowerCase().equals(name.toLowerCase())) {
                str = key;
                break;
            }
        }//for
        return str;
    }
}
