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
 * @version 2014年7月31日
 */
public class BenchMark {

    public volatile long totalTime;

    private CountDownLatch latch;

    private final int loop;

    private final int threads;

    private final float ratio;

    public BenchMark(final int loop, final int threads, final float ratio) {
        super();
        this.loop = loop;
        this.threads = threads;
        this.ratio = ratio;
    }
    class BenchMarkRunnable implements Runnable {

        private final MapWrapper mapWrapper;

        private final int size;

        public BenchMarkRunnable(final MapWrapper mapWrapper, final int size) {
            this.mapWrapper = mapWrapper;
            this.size = size;
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
    public void benchmark(final MapWrapper mapWrapper) {
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
        System.out.println("[" + mapWrapper.getName() + "]threadnum[" + threads + "]ratio[" + rwratio
            + "]avgtime[" + totalTime / 3 + "]");
    }

    public static void benchmark2(final int loop, final int threads, final float ratio) {
        final BenchMark benchMark = new BenchMark(loop, threads, ratio);
        final MapWrapper[] wrappers = new MapWrapper[] { new HashTableMapWrapper(),
        new SyncMapWrapper(),
        new LockMapWrapper(),
        new RWLockMapWrapper(),
        new ConcurrentMapWrapper(),
        new WriteLockMapWrapper()
        };
        for (final MapWrapper wrapper : wrappers) {
            benchMark.benchmark(wrapper);
        }
    }
    public static void test() {
        benchmark2(100, 10, 1);// r:w 1:1
        benchmark2(100, 10, 0.1f);// r:w 10:1
        benchmark2(100, 10, 0.01f);// r:w 100:1
        benchmark2(100, 10, 0.001f);// r:w 1000:1
        // ///
        benchmark2(100, 50, 0.1f);// r:w 10:1
        benchmark2(100, 50, 0.01f);// r:w 100:1
        benchmark2(100, 50, 0.001f);// r:w 1000:1
        // ///
        benchmark2(100, 100, 1f);// r:w 10:1
        benchmark2(100, 100, 0.1f);// r:w 10:1
        benchmark2(100, 100, 0.01f);// r:w 100:1
        benchmark2(100, 100, 0.001f);// r:w 1000:1
        // //
    }
    public static void main(final String[] args) {
        test();
    }
}

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
            // TODO: handle exception
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
        } finally {
            lock.unlock();
        }
    }
    @Override
    public String getName() {
        return "mutexlock";
    }
}

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
            // TODO: handle exception
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
interface MapWrapper {

    void put(Object key, Object value);

    Object get(Object key);

    void clear();

    String getName();
}