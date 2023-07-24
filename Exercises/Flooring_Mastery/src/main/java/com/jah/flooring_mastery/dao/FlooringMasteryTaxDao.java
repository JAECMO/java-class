/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.flooring_mastery.dao;

import com.jah.flooring_mastery.dto.Order;
import com.jah.flooring_mastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface FlooringMasteryTaxDao {
    
     List<Tax> getAllTax()
     throws FlooringMasteryPersistenceException;
    
    Tax getTax(String stateName)
     throws FlooringMasteryPersistenceException;
    
    String verifyStateName(String stateName)
            throws FlooringMasteryPersistenceException;
    
}
