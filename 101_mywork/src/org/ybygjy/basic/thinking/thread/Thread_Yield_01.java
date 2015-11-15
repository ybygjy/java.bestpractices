package org.ybygjy.basic.thinking.thread;

import java.util.concurrent.CountDownLatch;

/**
 * yieldThread
 * @author WangYanCheng
 * @version 2010-9-25
 */
public class Thread_Yield_01 extends Thread {
    /** countDown */
    private int countDown = 5;
    /** thread count */
    private static int thCount = 0;
    private volatile CountDownLatch latch;
    /**
     * Constructor
     */
    public Thread_Yield_01(CountDownLatch latch) {
        super("" + (thCount++));
        start();
        this.latch = latch;
    }

    @Override
    public void run() {
    	try {
			this.latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        while (true) {
            System.out.println(this);
            if (--countDown == 0) {
                break;
            }
            yield();
        }
    }

    @Override
    public String toString() {
        return "#" + getName() + ": " + countDown;
    }
}
