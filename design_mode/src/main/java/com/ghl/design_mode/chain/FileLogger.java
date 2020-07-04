package com.ghl.design_mode.chain;

public class FileLogger extends AbstractLogger {
    public FileLogger(int level) {
        this.level = level;
    }

    @Override
    void write(String msg) {
        System.out.println("File Console::Logger: " + msg);
    }
}
