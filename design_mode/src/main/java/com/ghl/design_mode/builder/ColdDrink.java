package com.ghl.design_mode.builder;

abstract class ColdDrink implements FoodItem {

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
