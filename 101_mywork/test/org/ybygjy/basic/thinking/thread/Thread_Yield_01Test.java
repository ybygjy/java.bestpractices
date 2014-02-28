package org.ybygjy.basic.thinking.thread;


import org.junit.Before;
import org.junit.Test;

public class Thread_Yield_01Test {

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread_Yield_01();
        }
    }
    @Test
    public void doTest() {
    }
}
