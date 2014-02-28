package org.ybygjy.basic.thinking.thread;


import org.junit.Before;
import org.junit.Test;

public class Thread_ResourceShare_01Test {

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void doTest() {
        Thread_ResourceShare_01 trInst = new Thread_ResourceShare_01();
        trInst.doTest();
        while (true) {
            trInst.next();
        }
    }
}
