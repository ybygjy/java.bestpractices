package org.ybygjy.basic.thinking.thread;

import java.util.ArrayList;
import java.util.List;
/**
 * 带有缺陷的Thread生产者和消费者模式
 * <p>1、多线程共享某一种资源
 * <p>2、没有考虑线程等待中外部中断的情况
 * @author WangYanCheng
 * @version 2012-8-30
 */
public class ThreadTest {
    public static void main(String[] args) {
        ConditionVariable cv = new ConditionVariable();
        new Th1(cv).start();
        new Th1(cv).start();
        new Th1(cv).start();
        new Th1(cv).start();
        new Th1(cv).start();
        new Th2(cv).start();
    }
}
class Th1 extends Thread {
    private ConditionVariable cv;
    public Th1(ConditionVariable cv) {
        this.cv = cv;
    }
    public void run() {
        while (true) {
            synchronized(cv) {
                while (cv.getLen()<=0) {
                    try {
                        cv.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //未考虑中断
                if (cv.getLen() > 0) {
                    System.out.println(getName() + "消费了==>" + cv.get(0));
                    cv.notifyAll();
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Th2 extends Thread {
    private ConditionVariable cv;
    private int counter;
    public Th2(ConditionVariable cv) {
        this.cv = cv;
    }
    public void run() {
        while (true) {
            synchronized (cv) {
                while (cv.getLen() > 0) {
                    try {
                        cv.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (cv.getLen() <=0) {
                    System.out.println(getName() + " 生产了：" + counter);
                    cv.push(String.valueOf(counter));
                    counter++;
                    cv.notifyAll();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class ConditionVariable {
    private List<String> stackArr = new ArrayList<String>();
    public synchronized int getLen() {
        return stackArr.size();
    }
    public synchronized void push(String key) {
        this.stackArr.add(key);
    }
    public synchronized String get(int index) {
        return stackArr.remove(index);
    }
}