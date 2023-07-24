/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.controller;

import com.jah.flooring_mastery.dao.FlooringMasteryFileCreationException;
import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Product;
import com.jah.flooring_mastery.dto.States;
import com.jah.flooring_mastery.dto.Tax;
import com.jah.flooring_mastery.service.AreaValidationException;
import com.jah.flooring_mastery.service.CustomerNameValidationException;
import com.jah.flooring_mastery.service.FlooringMasteryServiceLayer;
import com.jah.flooring_mastery.service.FolderPathValidationException;
import com.jah.flooring_mastery.service.OrderDateValidationException;
import com.jah.flooring_mastery.service.OrderNumberValidationException;
import com.jah.flooring_mastery.service.ProductTypeValidationException;
import com.jah.flooring_mastery.service.StateExistenceValidationException;
import com.jah.flooring_mastery.service.StateValidationException;
import com.jah.flooring_mastery.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws OrderDateValidationException, CustomerNameValidationException, AreaValidationException, StateValidationException, ProductTypeValidationException, OrderNumberValidationException, StateExistenceValidationException, NumberFormatException, FlooringMasteryFileCreationException, FolderPathValidationException {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() throws FlooringMasteryPersistenceException {
        view.displayDisplayOrdersBanner();
        LocalDate date = view.getOrdersDate();
        try {
            List<Order> orderList = service.getOrdersByDate(date);
            for (Order order : orderList) {
                String customerName = order.getCustomerName();
                customerName = customerName.replaceAll("#", ",");
                order.setCustomerName(customerName);
            }
            view.displayOrderByDateList(orderList);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void addOrder() throws OrderDateValidationException, CustomerNameValidationException, AreaValidationException, FlooringMasteryPersistenceException, StateValidationException, ProductTypeValidationException, FlooringMasteryFileCreationException, FolderPathValidationException {
        view.displayCreateOrderBanner();
        boolean hasErrors;
        LocalDate minDate = LocalDate.now().plusDays(1);
        LocalDate maxDate = minDate.plusMonths(1);
        do {

            try {
                //Check if Folder exists
                service.checkOrderFolderPath();
                //Date
                LocalDate orderDate = view.getNewOrderDate(minDate, maxDate);
                service.orderDateValidation(orderDate, minDate, maxDate);

                //Order Number
                int newOrderNumber = service.newOrderNumber();

                //Customer Name
                String customerName = view.getCustomerName();
                service.customerNameValidation(customerName);//Verify name if valid
                String updatedCustomerName = service.updateCustomerName(customerName);//Replace "," with "#"

                //Tax & State
                String stateName = view.getStateName();
                Tax tax = service.getTax(stateName);
                String stateAbb = States.valueOf(stateName.toUpperCase()).getAbbreviation();
                BigDecimal taxRate = tax.getTaxRate();

                //Products
                view.displayDisplayAllProductBanner();
                List<Product> productList = service.getAllProduct();
                view.displayProductTypeList(productList);//Display all products
                String productType = view.getProductType();
                Product productSelected = service.getProduct(productType);
                productType = productSelected.getProductType();
                BigDecimal costPerSquareFoot = productSelected.getCostPerSquareFoot();
                BigDecimal laborCostPerSquareFoot = productSelected.getLaborCostPerSquareFoot();

                //Area
                BigDecimal area = view.getArea();
                service.areaValidation(area);//verify if area is within boundary

                BigDecimal materialCost = service.materialCost(area, costPerSquareFoot);
                BigDecimal laborCost = service.laborCost(area, laborCostPerSquareFoot);
                BigDecimal taxValue = service.taxValue(materialCost, laborCost, taxRate);
                BigDecimal total = service.total(materialCost, laborCost, taxValue);

                //Preview of new Order
                view.displayOrder(newOrderNumber, orderDate, customerName, stateName, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxValue, total);

                //Choose to place Order or no
                boolean choice = view.choiceOfAction("ORDER", "");

                if (choice) {
                    Order newOrder = new Order(orderDate, newOrderNumber, updatedCustomerName, stateAbb, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxValue, total);
                    service.createOrder(orderDate, newOrder, newOrderNumber);
                    view.displaySuccessOrder(newOrderNumber, orderDate);
                }
                hasErrors = false;
            } catch (OrderDateValidationException | CustomerNameValidationException | AreaValidationException | StateValidationException | ProductTypeValidationException | FlooringMasteryFileCreationException | FolderPathValidationException | FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = view.choiceOfAction("CONTINUE", "");
            }
        } while (hasErrors);

    }

    private void editOrder() throws FlooringMasteryPersistenceException, OrderNumberValidationException, StateValidationException, ProductTypeValidationException, AreaValidationException, StateExistenceValidationException, NumberFormatException, FlooringMasteryFileCreationException, CustomerNameValidationException {
        view.displayEditOrderBanner();
        boolean hasErrors;
        do {

            try {
                //Date
                LocalDate orderDate = view.getOrdersDate();
                service.getOrdersByDate(orderDate);

                //Order Number
                int orderNumber = view.getOrderNumber();
                Order order = service.getOrder(orderNumber, orderDate);//Exception will be thrown if order number does not exist

                //Customer Name
                String customerName = view.getCustomerNameEdit(service.reverseUpdateCustomerName(order.getCustomerName()));
                service.customerNameValidationEdit(customerName);//Verify name if valid
                String updatedCustomerName = service.updateCustomerName(customerName);//Replace "," with "#"
                customerName = service.checkEditInputIfChanged(updatedCustomerName, order.getCustomerName());//Allow for empty or blank String return former input(with "#" replaced with ",") if it is the case.
                updatedCustomerName = service.updateCustomerName(customerName);//Replace "," with "#"

                //Tax & State
                String stateFullName = service.findStatesNameFromAbb(order.getState());
                String stateName = view.getStateNameEdit(stateFullName);

                stateName = service.checkEditInputIfChanged(stateName, stateFullName);//Allow for empty or blank String return former input
                System.out.println(stateName);
                Tax tax = service.getTax(stateName);
                String stateAbb = States.valueOf(stateName.toUpperCase()).getAbbreviation();
                BigDecimal taxRate = tax.getTaxRate();

                //Products
                view.displayDisplayAllProductBanner();
                List<Product> productList = service.getAllProduct();
                view.displayProductTypeList(productList);//Display all products
                String productType = view.getProductTypeEdit(order.getProductType());

                productType = service.checkEditInputIfChanged(productType, order.getProductType());//Allow for empty or blank String return former input
                Product productSelected = service.getProduct(productType);
                productType = productSelected.getProductType();
                BigDecimal costPerSquareFoot = productSelected.getCostPerSquareFoot();
                BigDecimal laborCostPerSquareFoot = productSelected.getLaborCostPerSquareFoot();

                //Area
                String areaStr = view.getAreaEdit(order.getArea());
                String str = service.checkEditInputIfChanged(areaStr, order.getArea().toString());
                BigDecimal area = new BigDecimal(str);
                service.areaValidation(area);//Verify if value is in above or equal to the fixed limit

                BigDecimal materialCost = service.materialCost(area, costPerSquareFoot);
                BigDecimal laborCost = service.laborCost(area, laborCostPerSquareFoot);
                BigDecimal taxValue = service.taxValue(materialCost, laborCost, taxRate);
                BigDecimal total = service.total(materialCost, laborCost, taxValue);

                //Preview of Edited Order
                view.displayOrder(orderNumber, orderDate, customerName, stateName, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxValue, total);

                //Choose to edit order or no
                boolean choice = view.choiceOfAction("EDIT", "");

                if (choice) {
                    Order editedOrder = new Order(orderDate, orderNumber, updatedCustomerName, stateAbb, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxValue, total);
                    service.editOrder(orderNumber, orderDate, editedOrder);
                    view.displaySuccessEditOrder(orderNumber, orderDate);
                }
                hasErrors = false;
            } catch (FlooringMasteryPersistenceException | OrderNumberValidationException | StateValidationException | ProductTypeValidationException | AreaValidationException | StateExistenceValidationException | CustomerNameValidationException e) {

                view.displayErrorMessage(e.getMessage());

                hasErrors = view.choiceOfAction("CONTINUE", "");
            } catch (NumberFormatException e) {
                String customMessage = "Invalid input format. Please enter a valid number.";
                view.displayErrorMessage(customMessage);
                hasErrors = view.choiceOfAction("CONTINUE", "");
            }
        } while (hasErrors);

    }

    private void removeOrder() throws FlooringMasteryPersistenceException, OrderNumberValidationException, FlooringMasteryFileCreationException {
        view.displayRemoveOrderBanner();
        boolean hasErrors;
        do {

            try {
                //Date
                LocalDate orderDate = view.getOrdersDate();
                service.getOrdersByDate(orderDate);//Exception will be thrown if Date does not exist

                int orderNumber = view.getOrderNumber();
                Order order = service.getOrder(orderNumber, orderDate);//Exception will be thrown if order Number does not exist for the entered date
                String updatedCustomerName = service.reverseUpdateCustomerName(order.getCustomerName());//Replace "#" with "," for the display
                //Preview or removed order
                view.displayOrder(orderNumber, orderDate, updatedCustomerName, order.getState(), order.getTaxRate(), order.getProductType(), order.getArea(), order.getCostPerSquareFoot(), order.getLaborCostPerSquareFoot(), order.getMaterialCost(), order.getLaborCost(), order.getTax(), order.getTotal());
                //Choose to remove order or no
                boolean choice = view.choiceOfAction("REMOVE", "");

                if (choice) {
                    service.removeOrder(orderNumber, orderDate);
                    view.displaySuccessRemoveOrder(orderNumber, orderDate);
                }
                hasErrors = false;

            } catch (FlooringMasteryPersistenceException | OrderNumberValidationException e) {

                view.displayErrorMessage(e.getMessage());

                hasErrors = view.choiceOfAction("CONTINUE", "");

            }
        } while (hasErrors);
    }

    private void exportAllData() throws FlooringMasteryPersistenceException, FlooringMasteryFileCreationException, FolderPathValidationException {
        view.displayExportAllDataBanner();

        boolean choiceView = view.choiceOfAction("VIEW", "");//Choose to view all the orders or no
        try {
            if (!service.getAllOrders().isEmpty()) {
                if (choiceView) {
                    viewAllOrders();
                }
                String exportLink = service.getExportLink();//get link fixed in the DAO
                //Choose to export all data to the link or no
                boolean choiceExport = view.choiceOfAction("EXPORT", exportLink);
                if (choiceExport) {

                    service.checkBackupFolderPath();
                    service.exportAllData();
                    view.displaySuccessExportAllData(exportLink);

                }
            } else {
                view.displayNoData();
            }
        } catch (FlooringMasteryFileCreationException | FolderPathValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void viewAllOrders() throws FlooringMasteryPersistenceException {
        List<List<Order>> orderListList = service.getAllOrders();
        for (List<Order> orderList : orderListList) {
            for (Order order : orderList) {
                String customerName = order.getCustomerName();
                customerName = customerName.replaceAll("#", ",");
                order.setCustomerName(customerName);
            }
        }
        view.displayOrderList(orderListList);
    }

}
