package org.ybygjy.basic.thinking.thread;

import java.util.Timer;
import java.util.TimerTask;

import org.ybygjy.basic.thinking.thread.testframework.Invariant;
import org.ybygjy.basic.thinking.thread.testframework.InvariantWatcher;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantFailure;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantOK;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantState;

/**
 * 线程主题_正确停止线程
 * @author WangYanCheng
 * @version 2010-10-8
 */
public class Thread_Stop extends Thread implements Invariant {
    /** threadCount */
    private static int threadCount;
    /** threadFlag */
    private int threadFlag;
    /** counter */
    private int counter;
    /** stop flag */
    private volatile boolean stop;

    /**
     * Constructor
     */
    public Thread_Stop() {
        threadFlag = threadCount++;
        start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Terminated: " + threadFlag);
                requestStop();
            }
        }, 4000);
    }

    @Override
    public void run() {
        while (!stop) {
            counter++;
        }
    }

    /**
     * requestStop
     */
    public void requestStop() {
        this.stop = true;
    }

    /**
     * {@inheritDoc}
     */
    public InvariantState invariant() {
        if (isAlive()) {
            return new InvariantOK();
        } else {
            return new InvariantFailure(threadFlag + ":" + counter);
        }
    }

    @Override
    public String toString() {
        return "Counter: " + threadFlag + ":" + counter;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                new InvariantWatcher(new Thread_Stop());
            }
            Thread.currentThread().join();
        } catch (InterruptedException intrr) {
            intrr.printStackTrace();
        }
    }
}
