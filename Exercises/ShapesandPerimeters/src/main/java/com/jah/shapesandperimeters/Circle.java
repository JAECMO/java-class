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
public class Circle extends Shape{
    private final double PI = Math.PI;
    
    private double area;
    private double perimeter;
    private double r;

    public Circle() {
        super();
    }

    public Circle(double r) {
        super();
        this.r = r;
    }

    @Override
    public double getArea() {
        area = PI*Math.pow(r, 2);
        return area;
    }


    @Override
    public double getPerimeter() {
        perimeter = 2*PI*r;
        return perimeter;
    }


    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
    
    
    
}
