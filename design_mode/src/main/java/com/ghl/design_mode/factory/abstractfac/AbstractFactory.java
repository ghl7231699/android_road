package com.ghl.design_mode.factory.abstractfac;

/**
 * 创建一个一系列相关或相互依赖对象的接口
 */
abstract class AbstractFactory {
    public abstract Shape getShape(String shape);

    public abstract Color getColor(String color);
}
