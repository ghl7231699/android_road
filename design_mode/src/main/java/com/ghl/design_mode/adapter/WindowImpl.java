package com.ghl.design_mode.adapter;

/**
 * 类描述：如果一个类要实现一个具有很多抽象方法的接口，但是本身只需要实现其中的部分方法，此时就需要一个中间类，
 * 但是又不希望直接使用过渡类，因此将此过渡类设计为抽象类最为合适，再让子类直接继承该抽象类便可以选择性的覆盖所需的方法了
 */
class WindowImpl extends WindowAdapter {
    @Override
    public void open() {
        System.out.println("窗口open");
    }

    @Override
    public void close() {
        System.out.println("窗口close");
    }
}
