package com.ghl.design_mode.visitor;

/**
 * 汽车检修的打印者
 */
public class CheckCar implements Visitor {
    public void visit(Engine engine) {
        System.out.println("Check engine");
    }

    public void visit(Body body) {
        System.out.println("Check body");
    }

    public void visit(Car car) {
        System.out.println("Check car");
    }
}