package org.ybygjy.basic.thinking.thread;

/**
 * {@link Thread#sleep(long)}
 * @author WangYanCheng
 * @version 2010-9-25
 */
public class Thread_Sleep_01 extends Thread {
    /** threadCount */
    private static int threadCount;
    /** threadDown */
    private int threadDown = 5;

    /**
     * Constructor
     */
    public Thread_Sleep_01() {
        super("" + ++threadCount);
        start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this);
            if (--threadDown == 0) {
                break;
            }
            try {
                sleep(100);
            } catch (InterruptedException inte) {
                inte.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return ("#" + getName() + ": " + threadDown);
    }
}
