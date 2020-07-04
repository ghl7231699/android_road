package com.ghl.design_mode.chain;

public abstract class AbstractLogger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;
    // 责任链的下一个元素

    protected AbstractLogger next;

    public void setNext(AbstractLogger next) {
        this.next = next;
    }

    public void logMsg(int level, String msg) {
        if (this.level <= level) {
            write(msg);
        }
        if (next != null) {
            next.logMsg(level, msg);
        }
    }

    abstract void write(String msg);
}
