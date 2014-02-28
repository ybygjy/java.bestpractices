package org.ybygjy.basic.collect;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ConcurrentModificationException异常，此异常通常在一个集合为多个线程使用的场景中。
 * <p><strong>解决办法：</strong></p>
 * <p>
 * 1、使用copy办法，如<pre>
 * String[] tmpStrArr = new String[arrayListInst.size()];
 * for (String tmp : tmpStrArr) {
 *  System.out.println(tmp);
 * }
 * </pre>
 * 2、使用特定的集合实例，需要花费性能代价
 * </p>
 * @author WangYanCheng
 * @version 2010-11-10
 */
public class ConcurrentExceptionArrayListTest {
    /** 集合 */
    private List<String> rtnList = /*new ArrayList<String>();*/new CopyOnWriteArrayList<String>();/*Collections.synchronizedList(new ArrayList<String>());*/
    /**
     * 数据生产者
     * @author WangYanCheng
     * @version 2011-6-16
     */
    class ProducterThread extends Thread {
        private boolean startFlag = true;
        private Random randInst;

        /**
         * Constructor
         */
        public ProducterThread() {
            randInst = new Random();
        }
        /**
         * 线程停止
         * @param startFlag
         */
        public void stopThread(boolean startFlag) {
            this.startFlag = startFlag;
        }
        /**
         * runnable
         */
        public void run() {
            while (this.startFlag) {
                rtnList.add(String.valueOf(randInst.nextDouble()));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 数据消费者
     * @author WangYanCheng
     * @version 2011-6-16
     */
    class ConsumerThread extends Thread {
        private boolean startFlag = true;
        /**
         * 线程停止
         * @param startFlag
         */
        public void stopThread(boolean startFlag) {
            this.startFlag = startFlag;
        }
        public void run() {
            while (this.startFlag) {
                doPrintList(rtnList.toArray(new String[rtnList.size()]));
                doPrintList(rtnList.iterator());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void doPrintList(Iterator<String> iterator) {
        for (;iterator.hasNext();) {
            System.out.print(iterator.next() + "\t");
        }
        System.out.println();
    }
    public void doPrintList(String[] listArr) {
        for (String str : listArr) {
            System.out.print(str.concat("\t"));
        }
        System.out.println();
    }
    /**
     * 生成消费者线程对象
     * @return {@link ConsumerThread}
     */
    public ConsumerThread getConsumerThread() {
        return new ConsumerThread();
    }
    /**
     * 生成生产者线程对象
     * @return {@link ProducterThread}
     */
    public ProducterThread getProducterThread() {
        return new ProducterThread();
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ConcurrentExceptionArrayListTest cowALT = new ConcurrentExceptionArrayListTest();
        cowALT.getProducterThread().start();
        cowALT.getConsumerThread().start();
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
