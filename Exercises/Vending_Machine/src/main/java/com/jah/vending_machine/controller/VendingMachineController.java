/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.controller;

import com.jah.vending_machine.dao.VendingMachinePersistenceException;
import com.jah.vending_machine.dto.Change;
import com.jah.vending_machine.dto.Item;
import com.jah.vending_machine.service.InsufficientFundsException;
import com.jah.vending_machine.service.NoItemInventoryException;
import com.jah.vending_machine.service.VendingMachineServiceLayer;
import com.jah.vending_machine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drjal
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() throws InsufficientFundsException, NoItemInventoryException {
    boolean keepGoing = true;
    int menuSelection = 0;
    try {
        while (keepGoing) {
            listItems();
            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    buyItem();
                    break;
                case 2:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    } catch (VendingMachinePersistenceException e) {
        view.displayErrorMessage(e.getMessage());
    }
}
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void listItems() throws VendingMachinePersistenceException {
    view.displayDisplayAllBanner();
     List<Item> itemList = service.getAllItems();
    view.displayItemList(itemList);
}
    
    private void buyItem() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        view.displayDisplayBuyItemBanner();
        boolean hasErrors;
        do {
            String itemName = view.getItemNameChoice();
            String verifiedName = service.verifyTitle(itemName);
            try {
                Item item = service.getItem(verifiedName);
                service.checkInventory(item);
                BigDecimal amount = view.getAmount();
                BigDecimal moneyLeft = service.moneyLeft(item, amount);
                service.checkIfSufficientFunds(moneyLeft);
                service.editItemInventory(verifiedName, item);
                int quarters = Change.getQuarter(moneyLeft);
                int dimes = Change.getDime(moneyLeft);
                int nickels = Change.getNickel(moneyLeft);
                int pennies = Change.getPenny(moneyLeft);
                view.displayChangeBanner();
                view.displayChange(quarters, dimes, nickels, pennies);
                hasErrors = false;
            } catch (NoItemInventoryException | InsufficientFundsException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());

                hasErrors = view.choiceOfAction();

            }
        } while (hasErrors);

    }
    
//    private boolean choiceOfAction() {
//        boolean pass, hasErrors = false;
//
//        do {
//            String choice = view.displayChoiceOfAction().toUpperCase();
//            switch (choice) {
//                case "YES":
//                    pass = false;
//                    hasErrors = true;
//                    break;
//                case "NO":
//                    pass = false;
//                    hasErrors = false;
//                    break;
//                default:
//                    view.displayWrongInputMessage();
//                    pass = true;
//                    break;
//            }
//        } while (pass);
//        return hasErrors;
//    }
     private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
    
}
