/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.vending_machine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author drjal
 */
public class Item {
    
    
    private String name;
    private BigDecimal cost;
    private int inventory;

    public Item(String name, BigDecimal cost, int inventory) {
        this.name = name;
        this.cost = cost;
        this.inventory = inventory;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.cost);
        hash = 23 * hash + this.inventory;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.inventory != other.inventory) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", cost=" + cost + ", inventory=" + inventory + '}';
    }

    
}
