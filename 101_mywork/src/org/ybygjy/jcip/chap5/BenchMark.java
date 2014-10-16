package org.ybygjy.jcip.chap5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.ybygjy.jcip.chap5.bct.BenchMarkMapWrapperImpl4HashTable;
import org.ybygjy.jcip.chap5.bct.BenchMarkMapWrapperImpl4ReentLock;
import org.ybygjy.jcip.chap5.bct.BenchMarkMapWrapperImpl4SynchroFactoryMap;

/**
 * 该类描述了对Java各容器的基本性能测试，需要注意该类的设计思想非常有意思
 * <p>源地址：http://www.kafka0102.com/2010/11/405.html</p>
 * <p>1、使用闭锁处理等待多个线程全部执行完成的问题</p>
 * <p>2、定义被测试容器接口统一测试方法</p>
 * @version 2014年7月31日
 */
public class BenchMark {
    /** 总耗时*/
    public volatile long totalTime;
    /** 闭锁_用于控制多个测试线程同时开始并等待同时结束标识*/
    private CountDownLatch latch;
    /** 各线程对参与测试的集合执行读或写循环的次数*/
    private final int loop;
    /** 测试时同时运行的线程数量*/
    private final int threads;
    /** 频率因子，用于控制参与测试容器的读/写频率*/
    private final float ratio;

    /**
     * 构造函数
     * @param loop 循环次数
     * @param threads 测试线程数量
     * @param ratio 测试容器读/写频率因子
     */
    public BenchMark(final int loop, final int threads, final float ratio) {
        this.loop = loop;
        this.threads = threads;
        this.ratio = ratio;
    }
    /**
     * 测试
     * @param mapWrapper
     */
    private void doTest(final BenchMarkMapWrapper mapWrapper) {
        final float size = loop * threads * ratio;
        totalTime = 0;
        for (int k = 0; k < 3; k++) {
            latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                new Thread(new BenchMarkRunnable(mapWrapper, (int) size, loop)).start();
            }
            try {
                latch.await();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            mapWrapper.clear();
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
            try {
                Thread.sleep(1000);
            } catch (final Exception e) {
            }
        }
        final int rwratio = (int) (1.0 / ratio);
        System.out.println("[" + mapWrapper.getName() + "]线程数[" + threads + "]读/写因子[" + rwratio + "]平均时间[" + totalTime / 3 + "]");
    }

    public static void doTest(final int loop, final int threads, final float ratio) {
        final BenchMark benchMark = new BenchMark(loop, threads, ratio);
        final BenchMarkMapWrapper[] wrappers = new BenchMarkMapWrapper[] {
	        new BenchMarkMapWrapperImpl4SynchroFactoryMap(),
	        new BenchMarkMapWrapperImpl4HashTable(),
	        new BenchMarkMapWrapperImpl4ReentLock(),
	        new RWLockMapWrapper(),
	        new ConcurrentMapWrapper(),
	        new WriteLockMapWrapper()
        };
        for (final BenchMarkMapWrapper wrapper : wrappers) {
            benchMark.doTest(wrapper);
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(final String[] args) {
        doTest(100, 10, 1);// r:w 1:1
        doTest(100, 10, 0.1f);// r:w 10:1
        doTest(100, 10, 0.01f);// r:w 100:1
        doTest(100, 10, 0.001f);// r:w 1000:1
        // ///
        doTest(100, 50, 0.1f);// r:w 10:1
        doTest(100, 50, 0.01f);// r:w 100:1
        doTest(100, 50, 0.001f);// r:w 1000:1
        // ///
        doTest(100, 100, 1f);// r:w 1:1
        doTest(100, 100, 0.1f);// r:w 10:1
        doTest(100, 100, 0.01f);// r:w 100:1
        doTest(100, 100, 0.001f);// r:w 1000:1
        // //
    }
}

/**
 * 采用重入锁只控制写锁并发的Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class WriteLockMapWrapper implements BenchMarkMapWrapper {
    private final Map<Object, Object> map;
    private final Lock lock;
    public WriteLockMapWrapper() {
        map = new HashMap<Object, Object>();
        lock = new ReentrantLock();
    }
    @Override
    public void clear() {
        lock.lock();
        try {
            map.clear();
        } catch (final Exception e) {
        } finally {
            lock.unlock();
        }
    }
    @Override
    public Object get(final Object key) {
        return map.get(key);
    }
    @Override
    public void put(final Object key, final Object value) {
        lock.lock();
        try {
            map.put(key, value);
        } catch (final Exception e) {
        } finally {
            lock.unlock();
        }
    }
    @Override
    public String getName() {
        return "writelock";
    }
}
