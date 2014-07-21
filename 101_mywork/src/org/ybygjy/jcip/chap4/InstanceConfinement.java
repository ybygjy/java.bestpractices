package org.ybygjy.jcip.chap4;

import java.util.ArrayList;
import java.util.List;

/**
 * 实例封闭
 * <p>将数据封装在对象内部，可以将数据的访问限制在对象的方法上，从而更容易确保线程在访问数据时总能持有正确的锁。</p>
 * <p>通过将封闭机制与合适的加锁策略结合，可以确保以线程安全的方式来使用非线程安全的对象。</p>
 * <p>Decorator模式</p>
 * @author WangYanCheng
 * @version 2014-7-21
 */
public class InstanceConfinement {
    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args) {
        InnerInstanceConfinement instanceConf = new InnerInstanceConfinement();
        new WriteInnerThread("WriteThread", instanceConf).start();
        new ReadInnerThread("ReadThread", instanceConf).start();
        new MonitorThread("MonitorThread", instanceConf).start();
    }
    /**
     * 写线程
     * @author WangYanCheng
     * @version 2014-7-21
     */
    static class WriteInnerThread extends Thread {
        private InnerInstanceConfinement instanceConfinement;
        public WriteInnerThread(String threadName, InnerInstanceConfinement instanceConfinement) {
            super(threadName);
            this.instanceConfinement = instanceConfinement;
        }
        public void run() {
            while (true) {
                Point point = new Point((int)(Math.random() * 100), (int)(Math.random() * 100));
                this.instanceConfinement.addPoint(point);
                System.out.println(getName() + ">>" + point);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 读线程
     * @author WangYanCheng
     * @version 2014-7-21
     */
    static class ReadInnerThread extends Thread {
        private InnerInstanceConfinement instanceConfinement;
        public ReadInnerThread(String threadName, InnerInstanceConfinement instanceConfinement) {
            super(threadName);
            this.instanceConfinement = instanceConfinement;
        }
        public void run() {
            while (true) {
                Point[] points = this.instanceConfinement.getAllPoints();
                for (Point point : points) {
                    System.out.println(getName() + ">>" + point);
                    this.instanceConfinement.removePoint(point);
                    //验证List#toArray]，此处实例的修改不会影响到被封闭的非线程安全集合中的对象
                    //因为{@link Point}是不可变对象，所以无法修改对象的属性，但可以改变对象的引用
//                    point.x = 0;
//                    point.y = 0;
//                    类似
//                    String s1 = "abc";
//                    String s2 = "abc";
//                    s2==s1?s2="abcd":s1;
                    point = new Point(0, 0);
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 负责封闭实例状态监控
     * @author WangYanCheng
     * @version 2014-7-21
     */
    static class MonitorThread extends Thread {
        private InnerInstanceConfinement instanceConfinement;
        public MonitorThread(String threadName, InnerInstanceConfinement instanceConfinement) {
            super(threadName);
            this.instanceConfinement = instanceConfinement;
        }
        public void run() {
            while (true) {
                System.out.println(getName() + ">>" + this.instanceConfinement.getAllPoints().length);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/**
 * 负责通过封闭机制确保线程安全
 * <p>1、封闭非线程安全的集合对象</p>
 * <p>2、采集同步机制确保对非线程安全集合对象的操作是线程安全的</p>
 * @author WangYanCheng
 * @version 2014-7-21
 */
class InnerInstanceConfinement {
    private final List<Point> pointList = new ArrayList<Point>();
    public synchronized void addPoint(Point point) {
        this.pointList.add(point);
    }
    public synchronized boolean containsPoint(Point point) {
        return pointList.contains(point);
    }
    public synchronized boolean removePoint(Point point) {
        //重入
        if (containsPoint(point)) {
            return pointList.remove(point);
        } else {
            System.out.println("Not contains=>" + point);
        }
        return false;
    }
    /**
     * deepCopy快照
     * @return rtnPoint {@link Point}
     */
    public Point[] getAllPoints() {
        return pointList.toArray(new Point[pointList.size()]);
    }
}
