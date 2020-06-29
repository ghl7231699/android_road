package com.ghl.design_mode.builder;

class VegBurger extends Burger {
    @Override
    public String name() {
        return "Veg Burger";
    }

    @Override
    public float price() {
        return 30.f;
    }
}
