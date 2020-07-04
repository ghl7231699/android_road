package com.ghl.design_mode.chain;

public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    void write(String msg) {
        System.out.println("Standard Console::Logger: " + msg);
    }
}
