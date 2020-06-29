package com.ghl.design_mode.factory.abstractfac;

class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
