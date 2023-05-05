/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.shapesandperimeters;

/**
 *
 * @author drjal
 */
public abstract class Shape {
    private String color = "white";

//    public Shape() {
//        this.color = color;
//    }
    
    

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    
    public abstract double getArea();
    public abstract double getPerimeter();
}
