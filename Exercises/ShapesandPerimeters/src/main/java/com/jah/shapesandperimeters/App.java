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
public class App {
    public static void main(String[] args) {
        double semi = 6;
        double side1 = 4;
        double side2 = 4; 
        double side3 = 4;
        double area = Math.sqrt(semi * (semi- side1) * (semi - side2) * (semi - side3));
         System.out.println(area);
        
        Square square = new Square();
        Rectangle rectangle = new Rectangle();
        Triangle triangle = new Triangle();
        Circle circle = new Circle();
        
        square.setSide(5);
        rectangle.setLength(5);
        rectangle.setWidth(7);
        triangle.setSide1(4);//we suppose triangle sides length fufill the conditions for a triangle...
        triangle.setSide2(4);
        triangle.setSide3(4);
        circle.setR(5);
        
        
        System.out.println(square.getArea());
        System.out.println(rectangle.getArea());
        System.out.println(triangle.getArea());
        System.out.println(circle.getArea());
        System.out.println(circle.getPerimeter());
        System.out.println(square.getColor());
      
      
    }
    
}
