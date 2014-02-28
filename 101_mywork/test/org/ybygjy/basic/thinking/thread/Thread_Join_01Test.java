package org.ybygjy.basic.thinking.thread;


import org.junit.Before;
import org.junit.Test;

public class Thread_Join_01Test {

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void doTest() {
        Thread_Join_01 tj01 = new Thread_Join_01(1000);
        Thread_Join_01 tj02 = new Thread_Join_01(1000);
        
        Thread_Join_01.Joiner tjjChild01 = tj01.getJoiner();
        Thread_Join_01.Joiner tjjChild02 = tj02.getJoiner();
        
        tj01.interrupt();
    }
}
