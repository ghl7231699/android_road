package com.ghl.design_mode.visitor;

/**
 * 具体访问者
 * <p>
 * 汽车打印访问者
 */
public class PrintCar implements Visitor {
    public void visit(Engine engine) {
        System.out.println("Visiting engine");
    }

    public void visit(Body body) {
        System.out.println("Visiting body");
    }

    public void visit(Car car) {
        System.out.println("Visiting car");
    }
}