package org.ybygjy.basic.thinking.thread;

import org.junit.Before;
import org.junit.Test;

public class Thread_Simple_01Test {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void doTest() {
        for (int i = 0; i < 20; i++) {
            new Thread_Simple_01();
        }
    }
}
