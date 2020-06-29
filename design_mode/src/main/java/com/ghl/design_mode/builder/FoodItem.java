package com.ghl.design_mode.builder;

interface FoodItem {
    String name();

    Packing packing();

    float price();
}
