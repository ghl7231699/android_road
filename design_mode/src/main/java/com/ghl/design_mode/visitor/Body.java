package com.ghl.design_mode.visitor;

/**
 * 具体的元素
 */
class Body implements Visitable {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
