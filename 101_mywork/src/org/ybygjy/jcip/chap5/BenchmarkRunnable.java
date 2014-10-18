package org.ybygjy.jcip.chap5;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 负责执行给定容器的测试任务
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchmarkRunnable implements Runnable {
	/**被测试容器*/
	private BenchmarkMapWrapper container;
	/**最大容器存储元素个数*/
	private int maxSize;
	/**循环次数*/
	private int loopTimes;
	/**耗时统计*/
	private long timeConsume;
	/**闭锁，负责在并发场景中统一处理事件*/
	private CountDownLatch latch;
	/**回调*/
	private BenchmarkRunnableCallback callback;
	/**
	 * 构造函数
	 * @param container {@link BenchmarkMapWrapper}
	 * @param maxSize 最大容器存储元素个数
	 * @param loopTimes 循环次数
	 */
	public BenchmarkRunnable(BenchmarkMapWrapper container, int maxSize, int loopTimes, CountDownLatch latch, BenchmarkRunnableCallback callback) {
		super();
		this.container = container;
		this.maxSize = maxSize;
		this.loopTimes = loopTimes;
		this.latch = latch;
		this.callback = callback;
	}

	@Override
	public void run() {
		final long beginningTime = System.nanoTime();
		innerDoWork();
		this.timeConsume = System.nanoTime() - beginningTime;
		if (this.callback != null) {
			this.callback.callback(this);
		}
	}

	/**
	 * 负责具体执行测试逻辑
	 */
	private void innerDoWork() {
		//1.初始随机数发生器
		Random random = new Random();
		//2.循环指定次数
		int i = 0;
//		int writeTimes = 0;
		while (i <= this.loopTimes) {
			Object key = random.nextInt(this.maxSize);
			if (this.container.get(key) == null) {
				this.container.put(key, key);
//				writeTimes++;
			}
			i++;
		}
//		System.out.println(Thread.currentThread().getName() + ":writeTimes:" + writeTimes);
		//更新闭锁阀值
		this.latch.countDown();
	}
	/**
	 * 取操作耗时（毫秒）
	 * @return
	 */
	public long getTimeConsume() {
		return this.timeConsume;
	}
}
