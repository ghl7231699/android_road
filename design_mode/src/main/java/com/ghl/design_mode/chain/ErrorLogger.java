package com.ghl.design_mode.chain;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    void write(String msg) {
        System.out.println("Error Console::Logger: " + msg);
    }
}
