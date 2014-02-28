package org.ybygjy.log4j;


import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.TestInterface;

public class ApacheLoggingTestTest {
    /**testInterface*/
    private TestInterface testInst;
    @Before
    public void setUp() throws Exception {
        testInst = new ApacheLoggingTest();
    }
    @Test
    public void doTest() {
        testInst.doTest();
    }
}
