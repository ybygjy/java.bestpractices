package org.ybygjy.basic.thinking.thread;


import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class Thread_Priorities_01Test {

    @Before
    public void setUp() throws Exception {
        Random rand = new Random(10);
        for (int i = 0; i < 10; i++) {
            new Thread_Priorities_01(1 + rand.nextInt(10));
        }
    }
    @Test
    public void doTest() {
    }
}
