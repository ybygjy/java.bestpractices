package org.ybygjy.jcip.chap5;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 此类负责对Java各容器的基本性能测试，注意该类的设计思想具备推广价值
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
    /** 各线程对参与测试集合执行读或写循环的次数*/
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
     * 内部类负责执行针对容器的基准测试
     * @author WangYanCheng
     * @version 2014年8月8日
     */
    class BenchMarkRunnable implements Runnable {
        /** 被测试容器*/
        private final MapWrapper mapWrapper;
        /** */
        private final int size;
        /**
         * Constructor
         * @param mapWrapper 被测试容器
         * @param mapSize 被测试容器存储元素的目标大小 
         */
        public BenchMarkRunnable(final MapWrapper mapWrapper, final int mapSize) {
            this.mapWrapper = mapWrapper;
            this.size = mapSize;
        }
        public void benchmarkRandomReadPut(final MapWrapper mapWrapper, final int loop) {
            final Random random = new Random();
            int writeTime = 0;
            for (int i = 0; i < loop; i++) {
                final int n = random.nextInt(size);
                if (mapWrapper.get(n) == null) {
                    mapWrapper.put(n, n);
                    writeTime++;
                }
            }
            System.out.println(Thread.currentThread().getName() + "_WriteTime=" + writeTime);
        }
        @Override
        public void run() {
            final long start = System.currentTimeMillis();
            benchmarkRandomReadPut(mapWrapper, loop);
            final long end = System.currentTimeMillis();
            totalTime += end - start;
            latch.countDown();
        }
    }
    public void doTest(final MapWrapper mapWrapper) {
        final float size = loop * threads * ratio;
        totalTime = 0;
        for (int k = 0; k < 3; k++) {
            latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                new Thread(new BenchMarkRunnable(mapWrapper, (int) size)).start();
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
        final MapWrapper[] wrappers = new MapWrapper[] { new HashTableMapWrapper(),
        new SyncMapWrapper(),
        new LockMapWrapper(),
        new RWLockMapWrapper(),
        new ConcurrentMapWrapper(),
        new WriteLockMapWrapper()
        };
        for (final MapWrapper wrapper : wrappers) {
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
        doTest(100, 100, 1f);// r:w 10:1
        doTest(100, 100, 0.1f);// r:w 10:1
        doTest(100, 100, 0.01f);// r:w 100:1
        doTest(100, 100, 0.001f);// r:w 1000:1
        // //
    }
}

/**
 * HashTable容器
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class HashTableMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;

    public HashTableMapWrapper() {
        map = new Hashtable<Object, Object>();
    }
    @Override
    public void clear() {
        map.clear();
    }
    @Override
    public Object get(final Object key) {
        return map.get(key);
    }
    @Override
    public void put(final Object key, final Object value) {
        map.put(key, value);
    }
    @Override
    public String getName() {
        return "hashtable";
    }
}

/**
 * 采用实例级别同步的Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class SyncMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;
    public SyncMapWrapper() {
        map = new HashMap<Object, Object>();
    }
    @Override
    public synchronized void clear() {
        map.clear();
    }
    @Override
    public synchronized Object get(final Object key) {
        return map.get(key);
    }
    @Override
    public synchronized void put(final Object key, final Object value) {
        map.put(key, value);
    }
    @Override
    public String getName() {
        return "synclock";
    }
}

/**
 * 采用重入锁控制读/写操作的Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class LockMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;
    private final Lock lock;
    public LockMapWrapper() {
        map = new HashMap<Object, Object>();
        lock = new ReentrantLock();
    }
    @Override
    public void clear() {
        lock.lock();
        try {
            map.clear();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    @Override
    public Object get(final Object key) {
        lock.lock();
        try {
            return map.get(key);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
    @Override
    public void put(final Object key, final Object value) {
        lock.lock();
        try {
            map.put(key, value);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    @Override
    public String getName() {
        return "mutexlock";
    }
}

/**
 * 带有可重入读写锁控制并发的Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class RWLockMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;
    private final Lock readLock;
    private final Lock writeLock;
    public RWLockMapWrapper() {
        map = new HashMap<Object, Object>();
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } catch (final Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Object get(final Object key) {
        readLock.lock();
        try {
            return map.get(key);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        writeLock.lock();
        try {
            map.put(key, value);
        } catch (final Exception e) {
        } finally {
            writeLock.unlock();
        }
    }
    @Override
    public String getName() {
        return "rwlock";
    }
}
/**
 * JDK1.5加入的并发容器Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class ConcurrentMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;
    public ConcurrentMapWrapper() {
        map = new ConcurrentHashMap<Object, Object>();
    }
    @Override
    public void clear() {
        map.clear();
    }
    @Override
    public Object get(final Object key) {
        return map.get(key);
    }
    @Override
    public void put(final Object key, final Object value) {
        map.put(key, value);
    }
    @Override
    public String getName() {
        return "concrrent";
    }
}

/**
 * 采用重入锁只控制写锁并发的Map集合
 * @author WangYanCheng
 * @version 2014年8月8日
 */
class WriteLockMapWrapper implements MapWrapper {
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
/**
 * 定义被测试Map集合的行为，定义参与测试的容器必须包括的行为，如put/get
 * @author WangYanCheng
 * @version 2014年8月8日
 */
interface MapWrapper {
    /**
     * 值存储
     * @param key 关键字
     * @param value 值
     */
    void put(Object key, Object value);

    /**
     * 依据关键字取值
     * @param key 关键字
     * @return rtnVal 值
     */
    Object get(Object key);

    /**
     * 测试完成后调用释放占用的内存空间
     */
    void clear();

    /**
     * 返回测试实体的标识
     * @return rtnName
     */
    String getName();
}