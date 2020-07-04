package com.ghl.design_mode.chain;

public class ChainPatternMain {
    public static void main(String[] args) {
        AbstractLogger loggerChain = getLoggers();

        loggerChain.logMsg(AbstractLogger.INFO, "This is an information.");

        loggerChain.logMsg(AbstractLogger.DEBUG,
                "This is a debug level information.");

        loggerChain.logMsg(AbstractLogger.ERROR,
                "This is an error information.");
    }

    private static AbstractLogger getLoggers() {
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNext(fileLogger);
        fileLogger.setNext(consoleLogger);

        return errorLogger;
    }
}
