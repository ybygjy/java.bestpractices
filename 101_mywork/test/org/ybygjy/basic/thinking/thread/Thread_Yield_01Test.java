package org.ybygjy.basic.thinking.thread;


import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

public class Thread_Yield_01Test {

    @Before
    public void setUp() throws Exception {
    	CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread_Yield_01(latch);
        }
        latch.countDown();
        Thread.currentThread().join();
    }
    @Test
    public void doTest() {
    }
}
