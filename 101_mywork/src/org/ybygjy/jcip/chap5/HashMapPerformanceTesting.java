package org.ybygjy.jcip.chap5;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
=======
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
>>>>>>> 158d2ea208de16f43c5005313a35bb9c01caa13a

/**
 * 负责实现{@link HashMap}与{@link ConcurrentHashMap}容器的性能测试
 * @author WangYanCheng
 * @version 2014年7月31日
 */
public class HashMapPerformanceTesting {
    /** 测试所需创建的线程数量*/
    private int threadNums;
    /** 各线程循环次数*/
    private int loopNums;
    /** 测试目标容器的读/写比率*/
    private float rwRatio;
    /** 被测试容器*/
    private final Map<Object, Object> targetContainer;
<<<<<<< HEAD
=======
    /** 记录总耗时*/
    private volatile long totalSpendTime;
>>>>>>> 158d2ea208de16f43c5005313a35bb9c01caa13a
    /**
     * 构造方法
     * @param threadNums 线程数量
     * @param loopNums 循环次数
     * @param rwRatio 读/写比率
     * @param targetContainer 被测试容器
     */
    public HashMapPerformanceTesting(int threadNums, int loopNums, float rwRatio, Map<Object, Object> targetContainer) {
        this.threadNums = threadNums;
        this.loopNums = loopNums;
        this.rwRatio = rwRatio;
        this.targetContainer = targetContainer;
<<<<<<< HEAD
    }
    /**
     * 测试启动入口
     */
    public void doTest() {
        
=======
        this.totalSpendTime = 0L;
    }
    /**
     * 测试启动入口
     * @throws InterruptedException 线程中断异常
     */
    public void doTest() throws InterruptedException {
        final CountDownLatch beginGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        final Random random = new Random((int)(this.threadNums * this.loopNums * this.rwRatio));
        for (int i = 1; i <= threadNums; i++) {
            new Thread("HMTThread_" + targetContainer.getClass().getSimpleName() + "_" + i){
                @Override
                public void run() {
                    try {
                        beginGate.await();
                        for (int t = 0; t < loopNums; t++) {
                            int key = random.nextInt();
                            if (targetContainer.get(key) == null) {
                                targetContainer.put(key, key);
                            }
                        }
                        endGate.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        long startTime = System.currentTimeMillis();
        beginGate.countDown();
        endGate.await();
        totalSpendTime = System.currentTimeMillis() - startTime;
System.out.println(targetContainer.getClass() + "#" + toString());
    }
    
    @Override
    public String toString() {
        return "HashMapPerformanceTesting [threadNums=" + threadNums + ", loopNums=" + loopNums
            + ", rwRatio=" + rwRatio + ", totalSpendTime="
            + totalSpendTime + "]";
>>>>>>> 158d2ea208de16f43c5005313a35bb9c01caa13a
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        //定义容器的抽象
        //定义闭锁#开始门
        //定义闭锁#结束门
        //定义初始入口，负责创建线程
<<<<<<< HEAD
=======
        /*这里在进行测试的时候出现问题，HashMap非线程安全，在测试过程中出现死锁！出现死锁的原因未还未找到！*/
        Map[] mapArray = {
//            new HashMap<Object, Object>(),
            new ConcurrentHashMap<Object, Object>(),
            Collections.synchronizedMap(new HashMap<Object, Object>()),
            new Hashtable<Object, Object>()
            };
        for (Map<Object, Object> targetMap : mapArray) {
            HashMapPerformanceTesting hmptInst = new HashMapPerformanceTesting(10, 100, 1F, targetMap);
            try {
                hmptInst.doTest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
>>>>>>> 158d2ea208de16f43c5005313a35bb9c01caa13a
    }
}
