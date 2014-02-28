package org.ybygjy.basic.thinking.thread;

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

    /**
     * Constructor
     */
    public Thread_Yield_01() {
        super("" + (thCount++));
        start();
    }

    @Override
    public void run() {
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
