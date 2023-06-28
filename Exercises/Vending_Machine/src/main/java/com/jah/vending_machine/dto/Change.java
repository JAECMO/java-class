/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author drjal
 */
public class Change {
  

    public static int getQuarter(BigDecimal itemCost) {
        return Change.calculateChange(itemCost)[0];
    }

    public static  int getDime(BigDecimal itemCost) {
         return Change.calculateChange(itemCost)[1];
    }

    public static int getNickel(BigDecimal itemCost) {
         return Change.calculateChange(itemCost)[2];
    }

    public static int getPenny(BigDecimal itemCost) {
        return Change.calculateChange(itemCost)[3];
    }


    
    

    private static int[] calculateChange(BigDecimal itemCost) {
        BigDecimal itemCostAdjusted = itemCost.multiply(new BigDecimal("100"));
        int quarterNb = quarterNb(itemCostAdjusted);
        int dimeNb = dimeNb(itemCostAdjusted);
        int nickelNb = nickelNb(itemCostAdjusted);
        int penniesNb = penniesNb(itemCostAdjusted);
        int[] changeArray = {quarterNb, dimeNb, nickelNb, penniesNb};
        return changeArray;
    }

    private static int quarterNb(BigDecimal itemCostAdjusted) {
        int quarterNb = itemCostAdjusted.divide(Coin.QUARTER.getValue(), 2, RoundingMode.HALF_UP).intValue();
        return quarterNb;
    }

    private static int dimeNb(BigDecimal itemCostAdjusted) {
        BigDecimal penniesLeft = itemCostAdjusted.subtract(new BigDecimal(String.valueOf(Change.quarterNb(itemCostAdjusted))).multiply(Coin.QUARTER.getValue()));
        int dimeNb = penniesLeft.divide(Coin.DIME.getValue(), 2, RoundingMode.HALF_UP).intValue();
        return dimeNb;
    }

    private static int nickelNb(BigDecimal itemCostAdjusted) {
        BigDecimal penniesLeft = itemCostAdjusted.subtract(new BigDecimal(String.valueOf(Change.dimeNb(itemCostAdjusted))).multiply(Coin.DIME.getValue()).add(new BigDecimal(String.valueOf(Change.quarterNb(itemCostAdjusted))).multiply(Coin.QUARTER.getValue())));
        int nickelNb = penniesLeft.divide(Coin.NICKEL.getValue(), 2, RoundingMode.HALF_UP).intValue();
        return nickelNb;
    }

    private static int penniesNb(BigDecimal itemCostAdjusted) {
        BigDecimal penniesLeft = itemCostAdjusted.subtract(new BigDecimal(String.valueOf(Change.dimeNb(itemCostAdjusted))).multiply(Coin.DIME.getValue()).add(new BigDecimal(String.valueOf(Change.quarterNb(itemCostAdjusted))).multiply(Coin.QUARTER.getValue())).add(new BigDecimal(String.valueOf(Change.nickelNb(itemCostAdjusted))).multiply(Coin.NICKEL.getValue())));
        int penniesNb = penniesLeft.intValue();
        return penniesNb;
    }

}
