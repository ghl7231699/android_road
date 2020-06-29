package com.ghl.design_mode.visitor;

class VisitorClient {
    public static void main(String[] args) {
        Car car = new Car();
        car.addVisit(new Body());
        car.addVisit(new Engine());

        Visitor print = new PrintCar();
        car.show(print);
    }
}
