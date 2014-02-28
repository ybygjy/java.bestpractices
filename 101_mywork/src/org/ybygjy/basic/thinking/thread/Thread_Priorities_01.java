package org.ybygjy.basic.thinking.thread;

/**
 * {@link Thread#setPriority(int)}
 * @author WangYanCheng
 * @version 2010-9-25
 */
public class Thread_Priorities_01 extends Thread {
    /** ThreadCount */
    private static int threadCount;
    /** threadDown */
    private int threadDown;
    /** volatile variable */
    private volatile double d;
    /** Ñ­»··§Öµ */
    public static int loopFlag = 100000;
    /**threadPriority*/
    private int threadPriority;

    /**
     * Constructor
     * @param threadPriority threadPriority
     */
    public Thread_Priorities_01(int threadPriority) {
        setPriority(threadPriority);
        this.threadPriority = threadPriority;
        start();
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 1; i < loopFlag; i++) {
                d = d + (Math.PI + Math.E) / (double) i;
            }
            System.out.println(this);
            if (--threadDown == 5) {
                return;
            }
        }
    }

    @Override
    public String toString() {
        return ("#" + getName() + ": " + threadCount + ": " + threadPriority);
    }
}
