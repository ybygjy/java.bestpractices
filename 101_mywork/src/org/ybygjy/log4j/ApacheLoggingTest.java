package org.ybygjy.log4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ybygjy.basic.TestInterface;

/**
 * 测试Apache Logging
 * @author WangYanCheng
 * @version 2010-10-26
 */
public class ApacheLoggingTest implements TestInterface {
    /**logger*/
    private static Log logger;
    /**
     * {@inheritDoc}
     */
    public void doTest() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
            "org.apache.commons.logging.impl.Jdk14Logger");
        LogFactory factory = LogFactory.getFactory();
        String[] attributeArr = factory.getAttributeNames();
        for (String str : attributeArr) {
            System.out.println(str);
        }
        testInfo();
        LogFactory.releaseAll();
        testInfo();
    }
    /**
     * testInfo
     */
    private void testInfo() {
        logger = LogFactory.getLog(this.getClass());
        System.out.println(logger);
        logger.error("HelloWorld");
        logger.debug("HelloDebug");
    }
}
