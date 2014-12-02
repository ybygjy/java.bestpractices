package org.ybygjy.log4j.javalog;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.ybygjy.basic.TestInterface;

/**
 * InfoLogging基础应用
 * @author WangYanCheng
 * @version 2010-10-19
 */
public class InfoLogging {
    /** logger */
    private static Logger logger;
    static {
        //System.setProperty("java.util.logging.config.file", ".");
        logger = Logger.getLogger("InfoLogging");
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws Exception Exception
     */
    public static void main(String[] args) throws Exception {
        logger.setLevel(new DefaultLevel());
        FileHandler fileHandler = new FileHandler("InfoLogging.xml");
        logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
        logger.addHandler(fileHandler);
        logger.info("Logging an Info-level message.");
        logger.info("ALL level");
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            System.out.println(handler);
        }
        logger.logp(Level.INFO, InfoLogging.class.toString(), "main", "Logging an Info-level message.");
        //new SimpleFilter().doTest();
    }
}

/**
 * CusLevel
 * @author WangYanCheng
 * @version 2010-10-19
 */
class DefaultLevel extends java.util.logging.Level {
    /** serial */
    private static final long serialVersionUID = -4518465269469400662L;

    /**
     * Constructor
     */
    public DefaultLevel() {
        this("DefaultLevel", -1);
    }

    /**
     * Constructor
     * @param name name
     * @param value value
     */
    protected DefaultLevel(String name, int value) {
        super(name, value);
    }
}

/**
 * SimpleFilter
 * @author WangYanCheng
 * @version 2010-10-19
 */
class SimpleFilter implements TestInterface {
    /** logger */
    private static Logger logger;
    static {
        logger = Logger.getLogger(SimpleFilter.class.toString());
    }

    /**
     * doSendMessage
     */
    public void doSendMes() {
        logger.log(Level.INFO, "A duck in the house!", this);
        logger.log(Level.INFO, "A nuclear overthere", InfoLogging.class);
    }

    /**
     * doChangeStrategy
     */
    public void doChangeStrategy() {
        logger.setFilter(new Filter() {
            public boolean isLoggable(LogRecord record) {
                Object[] objArray = record.getParameters();
                if (null != objArray) {
                    for (Object obj : objArray) {
                        System.out.println(obj);
                        if (obj instanceof SimpleFilter) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void doTest() {
        this.doSendMes();
        logger.addHandler(new ConsoleHandler());
        Handler[] handlers = logger.getHandlers();
        for (Handler handle : handlers) {
            handle.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return "自定义数据Formatter:" + record.getLoggerName() + "$" + record.getMessage() + "==>"
                        + record.getMillis();
                }
            });
        }
        this.doSendMes();
    }
}
