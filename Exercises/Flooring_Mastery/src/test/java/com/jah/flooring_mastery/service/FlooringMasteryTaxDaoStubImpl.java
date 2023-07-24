/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.service;

import com.jah.flooring_mastery.dao.FlooringMasteryPersistenceException;
import com.jah.flooring_mastery.dao.FlooringMasteryProductDao;
import com.jah.flooring_mastery.dao.FlooringMasteryTaxDao;
import com.jah.flooring_mastery.dto.Tax;
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
public class FlooringMasteryTaxDaoStubImpl implements FlooringMasteryTaxDao {

    private final String TAX_LIST_FILE_TEST;
    
    public FlooringMasteryTaxDaoStubImpl() {
        TAX_LIST_FILE_TEST = "SampleFileData/Data/Taxes.txt";
    }

    public FlooringMasteryTaxDaoStubImpl(String taxListTextFile) {
        TAX_LIST_FILE_TEST = taxListTextFile;
    }
    
    public static final String DELIMITER = ",";

    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public List<Tax> getAllTax() throws FlooringMasteryPersistenceException {
        loadTaxList();
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax getTax(String stateName) throws FlooringMasteryPersistenceException {
        loadTaxList();
        return taxes.get(stateName);
    
    }
    
        private Tax unmarshallTax(String taxAsText) throws FlooringMasteryPersistenceException {
    String[] taxTokens = taxAsText.split(DELIMITER);

    try {
        String stateAbbreviation = taxTokens[0];
        String stateName = taxTokens[1];
        BigDecimal taxRate = new BigDecimal(taxTokens[2]);
        
        return new Tax(stateAbbreviation, stateName, taxRate);
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // Handle the exception appropriately
        // For example, log an error message or perform error recovery

       throw new FlooringMasteryPersistenceException ("Error in your text file!!",e); // Return null or throw an exception indicating the failure
    }
}

    
    private void loadTaxList() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_LIST_FILE_TEST)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load tax list data into memory.", e);
        }
        scanner.nextLine();
        String currentLine;
        Tax currentTax;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getStateName(), currentTax);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public String verifyStateName(String stateName) throws FlooringMasteryPersistenceException {
        loadTaxList();
        String str = stateName;

        for (String key : taxes.keySet()) {
            if (key.toLowerCase().equals(stateName.toLowerCase())) {
                str = key;
                break;
            }
        }//for
        return str;
    }
    
}
