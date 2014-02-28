package org.ybygjy.basic.thinking.thread;

/**
 * 线程_资源共享
 * @author WangYanCheng
 * @version 2010-9-27
 */
public class Thread_ResourceShare_01 {
    /** inner variable */
    private volatile int i;

    /**
     * self increment of the inner variable
     */
    public void next() {
        i++;
        i++;
    }

    /**
     * getter value
     * @return i i
     */
    public int getValue() {
        return i;
    }

    /**
     * doTest
     */
    public void doTest() {
        new Thread() {
            public void run() {
                while (true) {
                    int val = getValue();
                    if (val % 2 != 0) {
                        System.out.println(val);
                        System.exit(0);
                    }
                }
            }
        }.start();
    }
}
