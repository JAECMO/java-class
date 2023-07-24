/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.ui;

import com.jah.vending_machine.dto.Item;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author drjal
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
    this.io = io;
}
    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Buy item");
        io.print("2. Exit");

        return io.readInt("Please select from the above choices.", 1, 2);
    }
    
    public String getItemNameChoice() {
        return io.readString("Please enter the Item name.");
    }
    
     public BigDecimal getAmount() {
        return io.readBigDecimal("Please enter the amount of money");
    }
     
     public void displayWrongInputMessage() {
        io.print("Wrong Input, type YES or NO");
    }
     
     public boolean choiceOfAction() {
        boolean pass, hasErrors = false;

        do {
            String choice = displayChoiceOfAction().toUpperCase();
            switch (choice) {
                case "YES":
                    pass = false;
                    hasErrors = true;
                    break;
                case "NO":
                    pass = false;
                    hasErrors = false;
                    break;
                default:
                    displayWrongInputMessage();
                    pass = true;
                    break;
            }
        } while (pass);
        return hasErrors;
    }
     
     public String displayChoiceOfAction(){
     return io.readString("Do you wish to enter a new amount of money OR choose another item?(YES or NO)");
     }
     
    public void displayChangeBanner() {
        io.print("=== This is your change ===");
    }

    public void displayChange(int quarters, int dimes, int nickels, int pennies) {
        io.print("You get " + quarters + " quarters " + dimes + " dimes " + nickels + " nickels " + pennies + " pennies");
        io.print("\n");
    }
     
     
    public void displayDisplayAllBanner() {
        io.print("=== List of All Items ===");
    }

    public void displayItemList(List<Item> itemsByName) {
        if (!itemsByName.isEmpty()) {
            io.print("=================================");
            io.print("Name            Cost    Inventory");
            io.print("=================================");
            for (int i = 0; i < itemsByName.size(); i++) {
                if (itemsByName.get(i).getInventory() > 0) {
                    String formattedName = itemsByName.get(i).getName();

                    String itemInfo = String.format("%s" + "       %s\n",
                            itemsByName.get(i).getCost(),
                            itemsByName.get(i).getInventory());

                    String paddedName = String.format("%-15s", formattedName);
                    io.print(paddedName + " " + itemInfo);
                }
            }
        } else {
            io.print("There are no items available!");
        }
        io.print("\n");

    }
        
    public void displayDisplayBuyItemBanner() {
        io.print("=== Buying Item ===");
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
