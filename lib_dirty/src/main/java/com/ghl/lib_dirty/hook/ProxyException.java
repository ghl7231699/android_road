package com.ghl.lib_dirty.hook;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ProxyException extends RuntimeException {
    public ProxyException() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        print(this);
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        print(this);
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        print(this);
        super.printStackTrace(s);
    }

    /**
     * 自定义打印堆栈信息，用来作为代码插入的hook点
     */
    public void print(RuntimeException e) {
    }
}
