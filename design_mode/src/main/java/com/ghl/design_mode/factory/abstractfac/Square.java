package com.ghl.design_mode.factory.abstractfac;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}