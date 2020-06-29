package com.ghl.design_mode.visitor;

/**
 * 抽象元素
 */
public interface Visitable {
    void accept(Visitor visitor);
}