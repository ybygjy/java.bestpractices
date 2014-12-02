package org.ybygjy.basic.collect;

import java.util.Iterator;
import java.util.Vector;

/**
 * Vector集合对象测试
 * @author WangYanCheng
 * @version 2010-11-10
 */
public class VectorTest {
    /**
     * 重新并发访问异常
     */
    public void doTestConcurrency() {
        final Vector vecInst = new Vector();
        vecInst.add("Hi");
        vecInst.add("GoGo");
        /**
         * ThreadA负责修改vecInst
         * @author WangYanCheng
         * @version 2010-11-10
         */
        class ThreadA extends Thread {
            public ThreadA() {
                setName("ThreadA");
            }
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    vecInst.add(System.currentTimeMillis());
                }
            }
        }
        /**
         * 负责遍历vecInst
         * @author WangYanCheng
         * @version 2010-11-10
         */
        class ThreadB extends Thread {
            public ThreadB() {
                setName("ThreadB");
            }
            @Override
            public void run() {
                while (true) {
                    oneStrategy();
                    twoStrategy();
                }
            }
            public void oneStrategy() {
                Object[] objArr = vecInst.toArray();
                for (Object obj : objArr) {
                    System.out.println(obj);
                }
            }
            public void twoStrategy() {
                Iterator iterator = vecInst.iterator();
                while (iterator.hasNext()) {
                    System.out.print(iterator.next());
                }
            }
        }
        new ThreadA().start();
        new ThreadB().start();
    }
    public static void main(String[] args) {
        new VectorTest().doTestConcurrency();
    }
}
