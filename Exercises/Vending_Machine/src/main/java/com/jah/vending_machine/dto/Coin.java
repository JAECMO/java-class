/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dto;

import java.math.BigDecimal;

/**
 *
 * @author drjal
 */
public enum Coin {
  PENNY(new BigDecimal("1")), NICKEL(new BigDecimal("5")), DIME(new BigDecimal("10")), QUARTER(new BigDecimal("25"));
  
    private final BigDecimal value ;

    private Coin(BigDecimal value) {
        this.value = value;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public BigDecimal getValue() {
        return value;
    }

}
