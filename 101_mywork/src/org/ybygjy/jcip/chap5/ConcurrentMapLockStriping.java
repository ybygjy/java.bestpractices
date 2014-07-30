package org.ybygjy.jcip.chap5;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * ConcurrentHashMap#分段锁学习实践
 * <p>1、分段锁应用实践</p>
 * <p>2、分段锁的实现分析</p>
 * @author WangYanCheng
 * @version 2014年7月30日
 */
public class ConcurrentMapLockStriping {
    /**
     * 测试入口
     */
    public void doTest() {
        //利用多线程验证ConcurrentHashMap与HashMap的性能
        //10个线程负责随机存数据
        doTestConcurrentHashMap(10);
    }
    /**
     * 执行入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ConcurrentMapLockStriping cmlsInst = new ConcurrentMapLockStriping();
        cmlsInst.doTest();
    }
    private void doTestConcurrentHashMap(int nThreads) {
        /** 闭锁_起始门*/
        final CountDownLatch startGate = new CountDownLatch(1);
        /** 闭锁_结束门*/
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        final ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>();
        for (int i = 1; i <= nThreads; i++) {
            new Thread("ConcurrentTestThread_" + i){
                public void run() {
                    try {
                        startGate.await();
                        try {
                            new ConcurrentHashMapTest(getName(), concurrentHashMap).run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        try {
            long startTime = System.currentTimeMillis();
            startGate.countDown();
            endGate.await();
            System.out.println("耗时：" + (new Double(System.currentTimeMillis() - startTime) / 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class ConcurrentHashMapTest implements Runnable {
        private ConcurrentHashMap<String, Object> concurrentHashMap;
        /** 循环次数*/
        private static final int MAX_CYCLECOUNT = 1000;
        private String threadName;
        public ConcurrentHashMapTest(String threadName, ConcurrentHashMap<String, Object> concurrentHashMap) {
            this.threadName = threadName;
            this.concurrentHashMap = concurrentHashMap;
        }
        @Override
        public void run() {
            int count = 1;
            while (count <= MAX_CYCLECOUNT) {
                String key = threadName + String.valueOf((int) (Math.random() * 1000));
                this.concurrentHashMap.put(key, String.valueOf(Math.random()));
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
