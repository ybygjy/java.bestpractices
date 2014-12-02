package org.ybygjy.basic.thinking.thread;

import org.ybygjy.basic.TestInterface;

/**
 * 简单线程
 * @author WangYanCheng
 * @version 2010-9-25
 */
public class Thread_Simple_01 extends Thread implements TestInterface {
    /** count */
    private static int threadCount = 0;
    /** countDown */
    private int countDown = 5;

    /**
     * Constructor
     */
    public Thread_Simple_01() {
        super("" + (++threadCount));
        start();
    }

    /**
     * run
     */
    public void run() {
        while (true) {
            System.out.println(this);
            if (--countDown == 0) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return ("#".concat(getName()) + ": " + countDown);
    }

    /**
     * {@inheritDoc}
     */
    public void doTest() {
    }
}
