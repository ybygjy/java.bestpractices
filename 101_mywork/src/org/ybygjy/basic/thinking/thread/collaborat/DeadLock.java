package org.ybygjy.basic.thinking.thread.collaborat;

import java.util.Random;

/**
 * 线程主题_哲学家吃饭问题
 * @author WangYanCheng
 * @version 2010-10-8
 */
public class DeadLock {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Philosopher[] philosopher = new Philosopher[9];
        Philosopher.ponder = 0;
        Chopstick leftChopstick = new Chopstick(), rightChopstick = new Chopstick(), first = leftChopstick;
        int i = 0;
        while (i < philosopher.length - 1) {
            philosopher[i++] = new Philosopher(leftChopstick, rightChopstick);
            leftChopstick = rightChopstick;
            rightChopstick = new Chopstick();
        }
        // dead lock
        philosopher[i] = new Philosopher(leftChopstick, first);
        // philosopher[i] = new Philosopher(first, leftChopstick);
    }
}

/**
 * Chopstick
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Chopstick {
    /** counter */
    private static int counter = 0;
    /** number */
    private int number = counter++;

    @Override
    public String toString() {
        return "Chopstick " + number;
    }
}

/**
 * Philosopher
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Philosopher extends Thread {
    /** random */
    private static Random rand;
    /** counter */
    public static int counter;
    /** number */
    private int number = counter++;
    /** left chopstick */
    private Chopstick leftChopstick;
    /** right chopstick */
    private Chopstick rightChopstick;
    /** package access */
    static int ponder;

    /**
     * Constructor
     * @param left {@link Chopstick}
     * @param right {@link Chopstick}
     */
    public Philosopher(Chopstick left, Chopstick right) {
        rand = new Random();
        this.leftChopstick = left;
        this.rightChopstick = right;
        start();
    }

    /**
     * thinking
     */
    public void think() {
        System.out.println(this + " thinking.");
        if (ponder > 0) {
            try {
                sleep(rand.nextInt(ponder));
            } catch (InterruptedException intrr) {
                throw new RuntimeException(intrr);
            }
        }
    }

    /**
     * eat
     */
    public void eat() {
        synchronized (leftChopstick) {
            System.out.println(this + " has " + this.leftChopstick + " Waiting for " + this.rightChopstick);
            synchronized (rightChopstick) {
                System.out.println(this + " eating.");
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            think();
            eat();
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + number;
    }
}
