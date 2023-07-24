/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.ui;

import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public class FlooringMasteryView {
    
    private UserIO io;
    
    public FlooringMasteryView(UserIO io) {
    this.io = io;
}
    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *"+"\n");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    

    
          public void displayOrderList(List<List<Order>> ordersByDate) {
        if (!ordersByDate.isEmpty()) {
            io.print("===============================================================================================================================================================================================================================");
            io.print("OrderNumber     CustomerName       State               TaxRate           ProductType             Area          CostPerSquareFoot   LaborCostPerSquareFoot  MaterialCost        LaborCost             Tax                Total");
            io.print("===============================================================================================================================================================================================================================");
            for (int i = 0; i < ordersByDate.size(); i++) {

                for (int j = 0; j < ordersByDate.get(i).size(); j++) {
                    String formattedNumber = String.valueOf(ordersByDate.get(i).get(j).getOrderNumber());

                    String orderInfo = String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
                            ordersByDate.get(i).get(j).getCustomerName(),
                            ordersByDate.get(i).get(j).getState(),
                            ordersByDate.get(i).get(j).getTaxRate(),
                            ordersByDate.get(i).get(j).getProductType(),
                            ordersByDate.get(i).get(j).getArea(),
                            ordersByDate.get(i).get(j).getCostPerSquareFoot(),
                            ordersByDate.get(i).get(j).getLaborCostPerSquareFoot(),
                            ordersByDate.get(i).get(j).getMaterialCost(),
                            ordersByDate.get(i).get(j).getLaborCost(),
                            ordersByDate.get(i).get(j).getTax(),
                            ordersByDate.get(i).get(j).getTotal());

                    String paddedNumber = String.format("%-15s", formattedNumber);
                    io.print(paddedNumber + " " + orderInfo);
                }
            }
        } else {
            io.print("There are no orders available!");
        }
        io.print("\n");

    }
    
        public void displayOrderByDateList(List<Order> ordersByDate) {
      if (!ordersByDate.isEmpty()) {
            io.print("\n"+ "Order Date: " + ordersByDate.get(0).getOrderDate());
            io.print("===============================================================================================================================================================================================================================");
            io.print("OrderNumber     CustomerName       State               TaxRate           ProductType             Area          CostPerSquareFoot   LaborCostPerSquareFoot  MaterialCost        LaborCost             Tax                Total");
            io.print("===============================================================================================================================================================================================================================");
            for (int i = 0; i < ordersByDate.size(); i++) {
                
                    String formattedNumber = String.valueOf(ordersByDate.get(i).getOrderNumber());

                    String orderInfo = String.format("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",
                            ordersByDate.get(i).getCustomerName(),
                            ordersByDate.get(i).getState(),
                            ordersByDate.get(i).getTaxRate(),
                            ordersByDate.get(i).getProductType(),
                            ordersByDate.get(i).getArea(),
                            ordersByDate.get(i).getCostPerSquareFoot(),
                            ordersByDate.get(i).getLaborCostPerSquareFoot(),
                            ordersByDate.get(i).getMaterialCost(),
                            ordersByDate.get(i).getLaborCost(),
                            ordersByDate.get(i).getTax(),
                            ordersByDate.get(i).getTotal());

                    String paddedNumber = String.format("%-15s", formattedNumber);
                    io.print(paddedNumber + " " + orderInfo);
                
            }
        } else {
            io.print("There are no orders available!");
        }
        io.print("\n");
    }
        
            public void displayProductTypeList(List<Product> productsByName) {
        if (!productsByName.isEmpty()) {
            io.print("================================================");
            io.print("Type            CostPerSqFt    LaborCostPerSqFt");
            io.print("================================================");
            for (int i = 0; i < productsByName.size(); i++) {
       
                    String formattedName = productsByName.get(i).getProductType();

                    String productInfo = String.format("%s" + "       %s\n",
                            productsByName.get(i).getCostPerSquareFoot(),
                            productsByName.get(i).getLaborCostPerSquareFoot());

                    String paddedName = String.format("%-15s", formattedName);
                    io.print(paddedName + " " + productInfo);
                
            }
        } else {
            io.print("There are no products available!");
        }
        io.print("\n");

    }

    public void displayWrongInputMessage() {
        io.print("Wrong Input, type YES or NO");
    }

    public String displayChoiceOfActionOrder() {
        return io.readString("Do you wish to place this order?(YES or NO)");
    }
    
    public String displayChoiceOfActionEdit() {
        return io.readString("Do you wish to Save the changes on this order?(YES or NO)");
    }
    
     public String displayChoiceOfActionRemove() {
        return io.readString("Do you wish to Remove this order?(YES or NO)");
    }
     
    public String displayChoiceOfActionExportAllData(String exportLink) {
        return io.readString("Do you wish to Export All the Data to " +exportLink + " ?"+"(YES or NO)");
    }

    public String displayChoiceOfActionViewAllData() {
        return io.readString("Do you wish to View All Orders before Exporting?(YES or NO)");
    }
    
    public String displayChoiceContinue() {
        return io.readString("Do you wish to Continue?(YES or NO)");
    }
    
    public boolean choiceOfAction(String choice, String exportLink) {
        boolean choiceBool = false,pass;
        String choiceYesNo="NO";
        do {
            switch (choice) {
                case "ORDER":
                    choiceYesNo = displayChoiceOfActionOrder().toUpperCase();
                    break;
                case "EDIT":
                    choiceYesNo = displayChoiceOfActionEdit().toUpperCase();
                    break;
                case "REMOVE":
                    choiceYesNo = displayChoiceOfActionRemove().toUpperCase();
                    break;
                case "EXPORT":
                    choiceYesNo = displayChoiceOfActionExportAllData(exportLink).toUpperCase();
                    break;
                case "VIEW":
                    choiceYesNo = displayChoiceOfActionViewAllData().toUpperCase();
                    break;
                 case "CONTINUE":
                    choiceYesNo = displayChoiceContinue().toUpperCase();
                    break;
            }
            switch (choiceYesNo) {
                case "YES":    
                    choiceBool = true;
                    pass = false;
                    break;
                case "NO":
                    choiceBool = false;
                     pass = false;
                    break;
                default:
                    displayWrongInputMessage();
                    pass = true;
                    break;
            }
        } while (pass);
        return choiceBool;
    }
    
    public void displayOrder(int newOrderNumber, LocalDate orderDate, String customerName, String stateName, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxValue, BigDecimal total) {
        io.print("Preview of Order # " + newOrderNumber);
        io.print("Order Date: " + orderDate);
        io.print("Customer Name: " + customerName);
        io.print("State: " + stateName);
        io.print("Tax Rate: " + taxRate);
        io.print("Product Type: " + productType);
        io.print("Area: " + area);
        io.print("Cost Per Sq Ft: " + costPerSquareFoot);
        io.print("Labor Cost Per Sq Ft: " + laborCostPerSquareFoot);
        io.print("Material Cost: " + materialCost);
        io.print("Labor Cost: " + laborCost);
        io.print("Tax: " + taxValue);
        io.print("Total: " + total);
    }
    
     public void  displaySuccessOrder(int newOrderNumber, LocalDate orderDate){
        io.print("Thank you! Your Order # "+newOrderNumber+ " was placed successfully for " + orderDate);
     }
     
     public void  displaySuccessEditOrder(int newOrderNumber, LocalDate orderDate){
        io.print("The changes on your Order # "+newOrderNumber+ " for " + orderDate + " were saved succesfully");
     }
     
     
     public void displaySuccessRemoveOrder(int orderNumber, LocalDate orderDate){
        io.print("Your Order # "+orderNumber+ " for " + orderDate + " was removed succesfully");
     }
     
    public void displaySuccessExportAllData(String exportLink){
        io.print("All your data were succesfully exported to "+exportLink);
     }
          
            
    public void displayDisplayAllProductBanner(){
    io.print("=== List of Products ===");
    }
        
     public void displayCreateOrderBanner() {
        io.print("=== Create Order ===");
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
    
    public LocalDate getNewOrderDate(LocalDate minDate, LocalDate maxDate) {
        return io.readDate("Enter an order date between " + minDate + " and " + maxDate);
    }
    
    public String getCustomerName(){
        return io.readString("Enter the customer name (May not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters)");
    }
    
    public String getStateName(){
       return io.readString("Enter the state name");
    }
    
    public String getProductType(){
       return io.readString("Enter the product type from the List above");
    }
    
    public BigDecimal getArea(){
        return io.readBigDecimal("Enter the area floor area in sq ft (Min order: 100 sq ft)");
    }
    
    public LocalDate getOrdersDate() {
        return io.readDate("Enter the order's date");
    }
    
    public Integer getOrderNumber() {
        return io.readInt("Enter the order's number");
    }
    
    public String getCustomerNameEdit(String presentCustomerName) {
        return io.readString("Enter customer name " + "(" + presentCustomerName + "):");
    }
    
    public String getStateNameEdit(String presentStateName) {
        return io.readString("Enter the state name " + "(" + presentStateName + "):");
    }

    public String getProductTypeEdit(String presentProductType) {
        return io.readString("Enter the product type " + "(" + presentProductType + "):");
    }
    
    public String getAreaEdit(BigDecimal presentArea){
    return io.readString("Enter area " + "(" + presentArea  +"):" );
    }
    
    public void displayDisplayOrdersBanner(){
         io.print("=== Display Orders By Date ===");
    }
    
      public void displayEditOrderBanner() {
        io.print("=== Edit Order ===");
    }
      
      public void displayRemoveOrderBanner() {
        io.print("=== Remove Order ===");
        
    }
      
    public void displayExportAllDataBanner() {
        io.print("=== Export All Data ===");
    }
    
    public void displayNoData() {
        io.print("There are no data to export!!");
    }
}
