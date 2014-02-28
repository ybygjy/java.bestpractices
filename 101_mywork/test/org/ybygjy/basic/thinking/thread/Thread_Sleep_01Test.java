package org.ybygjy.basic.thinking.thread;


import org.junit.Before;
import org.junit.Test;

public class Thread_Sleep_01Test {

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread_Sleep_01();
        }
        //考虑join()替代主线程sleep(100);
        Thread.sleep(10000);
    }
    @Test
    public void doTest() {
    }
}
