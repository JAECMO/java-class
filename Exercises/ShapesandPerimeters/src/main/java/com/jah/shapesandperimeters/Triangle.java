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
public class Triangle extends Shape {
    private double area;
    private double perimeter;
    private double side1;
    private double side2;
    private double side3;
    private double semi; 

    
    
     public Triangle() {
        super();
    }
    
    public Triangle (double side1, double side2, double side3 ) {
        super();
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public double getArea() {
       // semi = (side1 + side2 + side3)/2;
        semi = getSemi();
        area = Math.sqrt(semi * (semi- side1) * (semi - side2) * (semi - side3));
        return area;
    }


    @Override
    public double getPerimeter() {
        perimeter = side1+side2+side3;
        return perimeter;
    }

//    public double getSide1() {
//        return side1;
//    }

    public void setSide1(double side1) {
        this.side1 = side1;
    }

//    public double getSide2() {
//        return side2;
//    }

    public void setSide2(double side2) {
        this.side2 = side2;
    }

    public double getSemi() {
        semi = (side1 + side2 + side3)/2;
        return semi;
    }

//    public double getSide3() {
//        return side3;
//    }

    public void setSide3(double side3) {
        this.side3 = side3;
    }
    
    
}
