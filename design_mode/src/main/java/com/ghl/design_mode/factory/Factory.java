package com.ghl.design_mode.factory;

/**
 * 工厂模式
 * <p>
 * 作为在接口和子类之间过渡层，通过这个factory类可以动态的获取实现了这个接口的实例化对象
 */
class Factory {
    public static Animal getInstance(String name) {
        switch (name) {
            case "Cat":
                return new Cat();
            case "Dog":
                return new Dog();
            default:
                return null;
        }
    }
}
