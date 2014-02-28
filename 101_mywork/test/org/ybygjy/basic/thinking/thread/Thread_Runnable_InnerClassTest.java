package org.ybygjy.basic.thinking.thread;

import org.junit.Before;
import org.junit.Test;

public class Thread_Runnable_InnerClassTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void doTest() {
        new Thread_Runnable_InnerClass().doTest();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
