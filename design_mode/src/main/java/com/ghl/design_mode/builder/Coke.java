package com.ghl.design_mode.builder;

class Coke extends ColdDrink {
    @Override
    public String name() {
        return "Coke";
    }

    @Override
    public float price() {
        return 10.f;
    }
}
