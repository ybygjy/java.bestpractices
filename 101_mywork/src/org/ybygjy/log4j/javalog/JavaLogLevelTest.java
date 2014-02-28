package org.ybygjy.log4j.javalog;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.ybygjy.basic.TestInterface;
import org.ybygjy.test.TestUtils;

/**
 * Java LogLevelTest
 * @author WangYanCheng
 * @version 2010-10-20
 */
public class JavaLogLevelTest implements TestInterface {
    /** logger */
    private static Logger rootLog, child0, child1, child11, child01, otherRoot;

    static {
        rootLog = Logger.getLogger("org.ybygjy");
        child0 = Logger.getLogger("org.ybygjy.basic");
        child1 = Logger.getLogger("org.ybygjy.log4j");
        child01 = Logger.getLogger("org.ybygjy.basic.bit");
        child11 = Logger.getLogger("org.ybygjy.log4j.javalog");
        otherRoot = Logger.getLogger("org.hk");
    }

    /**
     * doWork
     */
    public void doWork() {
        logMessage(rootLog);
        logMessage(child0);
        logMessage(child1);
        logMessage(child01);
        logMessage(child11);
        logMessage(otherRoot);
    }

    /**
     * LogMessage
     * @param logger logger
     */
    public void logMessage(Logger logger) {
        logger.finest(logger.getName() + " Finest.");
        logger.finer(logger.getName() + " Finer.");
        logger.fine(logger.getName() + " Fine.");
        logger.config(logger.getName() + " Config.");
        logger.info(logger.getName() + " Info.");
        logger.warning(logger.getName() + " Warning.");
        logger.severe(logger.getName() + " Severe.");
    }

    /**
     * doPrintLevel
     * @return infoStr infoStr
     */
    public Map<String, String> doPrintLevel() {
        Map<String, String> infoStr = new HashMap<String, String>();
        infoStr.put(rootLog.getName(), rootLog.getLevel() + "");
        infoStr.put(child0.getName(), child0.getLevel() + "");
        infoStr.put(child01.getName(), child01.getLevel() + "");
        infoStr.put(child1.getName(), child1.getLevel() + "");
        infoStr.put(child11.getName(), child11.getLevel() + "");
        infoStr.put(otherRoot.getName(), otherRoot.getLevel() + "");
        return infoStr;
    }

    /**
     * {@inheritDoc}
     */
    public void doTest() {
        rootLog.setUseParentHandlers(false);
        Handler defaultHandler = new ConsoleHandler();
        defaultHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return "类名称空间Formatter组件的继承性==>" + record.getLoggerName() + ":" + record.getMessage() + "\n";
            }
        });
        defaultHandler.setLevel(Level.ALL);
        rootLog.addHandler(defaultHandler);
        rootLog.setLevel(Level.OFF);
        TestUtils.doPrint(doPrintLevel());
        this.doWork();
        rootLog.setLevel(Level.FINEST);
        TestUtils.doPrint(doPrintLevel());
        this.doWork();
        child01.setLevel(Level.FINEST);
        child11.setLevel(Level.FINEST);
        TestUtils.doPrint(doPrintLevel());
        this.doWork();
        rootLog.setLevel(Level.FINEST);
        TestUtils.doPrint(doPrintLevel());
        this.doWork();
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        JavaLogLevelTest jlltInst = new JavaLogLevelTest();
        jlltInst.doTest();
    }
}
