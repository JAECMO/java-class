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
public class Square extends Shape {
    private double area;
    private double perimeter;
    private double side;
    
    public Square() { // don't even need to add this constructor
        //super();
    }
    
    public Square(double side) { // don't even need to add this constructor
        //super();
        this.side = side;
        
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }


    
    @Override
    public double getArea() {
        return side * side;
    }


    @Override
    public double getPerimeter() {
        return 4 * (side);
    }

}
