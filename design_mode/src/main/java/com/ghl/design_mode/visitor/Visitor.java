package com.ghl.design_mode.visitor;

interface Visitor {
    void visit(Engine engine);

    void visit(Car car);

    void visit(Body body);
}
