package org.ybygjy.basic.thinking.thread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 线程主题_中断
 * @author WangYanCheng
 * @version 2010-10-8
 */
public class Thread_Interrupted {

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        final InnerClass[] threadArray = new InnerClass[3];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new InnerClass();
        }
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Terminated.");
                for (int i = threadArray.length - 1; i >= 0; i--) {
                    threadArray[i].interrupt();
                    threadArray[i] = null;
                }
            }
        }, 1000);
        try {
            Thread.sleep(2000);
            System.out.println("Thread.sleep(2000)");
        } catch (InterruptedException intrr) {
            intrr.printStackTrace();
        }
    }
}

/**
 * InnerCompiler
 * @author WangYanCheng
 * @version 2010-10-8
 */
class InnerClass extends Thread {
    /** threadCount */
    private static int threadCounter;
    /** threadName */
    private int threadName;

    /**
     * Contructor
     */
    public InnerClass() {
        threadName = threadCounter++;
        start();
    }

    @Override
    public void run() {
        System.out.println(this);
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException intrr) {
            System.out.println("打破中断: " + intrr);
        }
        System.out.println(this + "Exiting run()");
    }

    @Override
    public String toString() {
        return "Thread_" + threadName;
    }
}
