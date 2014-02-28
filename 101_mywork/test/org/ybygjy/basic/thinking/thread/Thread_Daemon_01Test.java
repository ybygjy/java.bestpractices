package org.ybygjy.basic.thinking.thread;


import org.junit.Before;
import org.junit.Test;

public class Thread_Daemon_01Test {

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void doTest() {
        for (int i = 0; i < 10; i++) {
            new Thread_Daemon_01();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interE) {
            interE.printStackTrace();
        }
    }
}
