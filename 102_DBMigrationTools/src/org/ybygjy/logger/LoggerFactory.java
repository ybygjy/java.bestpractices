package org.ybygjy.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 提供统一的日志对象生命周期处理
 * @author WangYanCheng
 * @version 2012-4-16
 */
public class LoggerFactory {
    /** Root Logger */
    private Logger rootLogger;
    /**日志文件路径*/
    private static String logFilePath = "./DataMigrationLog%g.log";
    /** singleton instance*/
    private static final LoggerFactory lfInst;
    static {
        lfInst = new LoggerFactory();
    }

    /**
     * 构造函数初始化
     */
    private LoggerFactory() {
        rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        try {
            Handler fileHandler = new FileHandler(logFilePath, false);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add Logger Handler
     * @param handler {@link Handler}
     */
    public void addLoggerHandler(Handler handler) {
        rootLogger.addHandler(handler);
    }

    /**
     * Remove Logger Handler
     * @param handler {@link Handler}
     */
    public void removeLoggerHandler(Handler handler) {
        rootLogger.removeHandler(handler);
    }

    /**
     * 取日志记录对象
     * @param name a name for the Logger
     * @return a suitable Logger
     * @see Logger#getLogger(String)
     */
    public Logger getLogger(String name) {
        return Logger.getLogger(name);
    }

    /**
     * 取日志记录对象
     * @param name a name for the Logger
     * @return a suitable Logger
     * @see Logger#getLogger(String)
     */
    public Logger getLogger() {
        return rootLogger;
    }

    /**
     * 取得日志统一管理实例
     * @return lfInst {@link LoggerFactory}
     */
    public static LoggerFactory getInstance() {
        return lfInst;
    }

    /**
     * 取日志文件路径
     * @return logFilePath 日志文件路径
     */
    public static String getLogFilePath() {
        return logFilePath;
    }
}
