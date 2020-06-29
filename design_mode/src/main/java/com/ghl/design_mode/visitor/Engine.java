package com.ghl.design_mode.visitor;

class Engine implements Visitable {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
