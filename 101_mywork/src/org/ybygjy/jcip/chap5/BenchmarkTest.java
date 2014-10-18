package org.ybygjy.jcip.chap5;

import java.util.concurrent.CountDownLatch;

import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4ConcurrentMap;
import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4HashTable;
import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4RWLock;
import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4ReentLock;
import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4SynchroFactoryMap;
import org.ybygjy.jcip.chap5.bct.BenchmarkMapWrapperImpl4WriteLock;

/**
 * 该类描述了对Java各容器的基本性能测试，需要注意原作者的设计思路非常具有通用性
 * <p>源地址：http://www.kafka0102.com/2010/08/298.html</p>
 * <p>1、使用闭锁处理等待多个线程全部执行完成的问题</p>
 * <p>2、定义被测试容器接口统一测试入口</p>
 * @version 2014年7月31日
 */
public class BenchmarkTest {
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
    public BenchmarkTest(final int loop, final int threads, final float ratio) {
        this.loop = loop;
        this.threads = threads;
        this.ratio = ratio;
    }
    /**
     * 测试
     * @param mapWrapper
     */
    private void doTest(final BenchmarkMapWrapper mapWrapper) {
        final float maxSize = loop * threads * ratio;
        totalTime = 0;
        //执行4次取平均耗时
        for (int k = 0; k < 4; k++) {
            latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
            	BenchmarkRunnable bmrInst = new BenchmarkRunnable(mapWrapper, (int) maxSize, loop, latch, new BenchmarkRunnableCallback() {
					@Override
					public void callback(BenchmarkRunnable benchmarkRunnable) {
						totalTime += benchmarkRunnable.getTimeConsume();
					}
				});
                new Thread(bmrInst, mapWrapper + "_" + i).start();
//System.out.println(mapWrapper.getName() + ":" + this.totalTime);
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
            	e.printStackTrace();
            }
        }
        System.out.println("[" + mapWrapper.getName() + "]线程数[" + threads + "]读/写因子[" + this.ratio + "]平均时间(秒)[" + (totalTime / 4.0)/(1000*1000*1000) + "]");
    }

    /**
     * 测试入口
     * @param loop 循环次数
     * @param threads 线程数量
     * @param ratio R/W因子
     */
    public static void doTest(final int loop, final int threads, final float ratio) {
        final BenchmarkTest benchMark = new BenchmarkTest(loop, threads, ratio);
        final BenchmarkMapWrapper[] wrappers = new BenchmarkMapWrapper[] {
	        new BenchmarkMapWrapperImpl4SynchroFactoryMap(),
	        new BenchmarkMapWrapperImpl4HashTable(),
	        new BenchmarkMapWrapperImpl4ReentLock(),
	        new BenchmarkMapWrapperImpl4WriteLock(),
	        new BenchmarkMapWrapperImpl4ConcurrentMap(),
	        new BenchmarkMapWrapperImpl4RWLock()
        };
        for (final BenchmarkMapWrapper wrapper : wrappers) {
            benchMark.doTest(wrapper);
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(final String[] args) {
        doTest(100, 10, 1);// r:w 1:1
        /*
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
        */
    }
}
