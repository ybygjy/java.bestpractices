package org.ybygjy.log4j;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Log4j测试
 * @author WangYanCheng
 * @version 2010-8-18
 */
public class Log4jTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("HelloWorld");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));
        try {
            logger.addAppender(new FileAppender(new PatternLayout(), "C:\\abcdef.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("HelloWorld");
    }
}
