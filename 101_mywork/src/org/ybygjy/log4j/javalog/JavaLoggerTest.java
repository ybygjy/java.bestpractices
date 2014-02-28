package org.ybygjy.log4j.javalog;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class JavaLoggerTest {
    private Logger logger = Logger.getLogger("");
    public void doWork() {
        Logger innerLogger = Logger.getLogger(this.getClass().toString());
        innerLogger.info("HelloWorld");
    }
    private void registerHandler() {
        logger.addHandler(new ConsoleHandler(){
            @Override
            protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        });
    }
    public static void main(String[] args) {
        JavaLoggerTest jltInst = new JavaLoggerTest();
        jltInst.registerHandler();
        jltInst.doWork();
    }
}
