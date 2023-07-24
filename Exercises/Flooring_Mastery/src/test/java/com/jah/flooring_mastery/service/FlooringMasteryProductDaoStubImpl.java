/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;

import com.jah.flooring_mastery.dao.FlooringMasteryOrderDao;
import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDao;
import com.jah.flooring_mastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao  {

    private final String PRODUCT_LIST_FILE_TEST;
    
    public FlooringMasteryProductDaoStubImpl() {
        PRODUCT_LIST_FILE_TEST = "SampleFileDataTest/Data/Products.txt";
    }

    public FlooringMasteryProductDaoStubImpl(String productListTextFile) {
        PRODUCT_LIST_FILE_TEST = productListTextFile;
    }
    
    public static final String DELIMITER = ",";

    private Map<String, Product> products = new HashMap<>();

    @Override
    public List<Product> getAllProduct() throws FlooringMasteryPersistenceException {
        loadProductList();
        return new ArrayList(products.values());
    }
    
     @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        loadProductList();
        return products.get(productType);
    }
    
        private Product unmarshallProduct(String productAsText) throws FlooringMasteryPersistenceException {
        String[] productTokens = productAsText.split(DELIMITER);

    try {
         String productType = productTokens[0];
         BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
         BigDecimal laborCostPerSquareFoot = new BigDecimal(productTokens[2]);
         
        
        return new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // Handle the exception appropriately
        // For example, log an error message or perform error recovery

       throw new FlooringMasteryPersistenceException ("Error in your text file!!",e); // Return null or throw an exception indicating the failure
    }
}

    
    private void loadProductList() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_LIST_FILE_TEST)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load product list data into memory.", e);
        }
        scanner.nextLine();
        String currentLine;
        Product currentProduct;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public String verifyProductName(String productType) throws FlooringMasteryPersistenceException {
        loadProductList();
        String str = productType;

        for (String key : products.keySet()) {
            if (key.toLowerCase().equals(productType.toLowerCase())) {
                str = key;
                break;
            }
        }//for
        return str;
    }

    
}
