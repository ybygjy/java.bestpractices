package org.ybygjy.jcip.chap5;

import java.util.Random;

/**
 * 负责执行给定容器的测试任务
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchMarkRunnable implements Runnable {
	/**被测试容器*/
	private BenchMarkMapWrapper container;
	/**最大容器存储元素个数*/
	private int maxSize;
	/**循环次数*/
	private int loopTimes;
	/**耗时统计*/
	private long timeConsume;
	/**
	 * 构造函数
	 * @param container {@link BenchMarkMapWrapper}
	 * @param maxSize 最大容器存储元素个数
	 * @param loopTimes 循环次数
	 */
	public BenchMarkRunnable(BenchMarkMapWrapper container, int maxSize, int loopTimes) {
		super();
		this.container = container;
		this.maxSize = maxSize;
		this.loopTimes = loopTimes;
	}

	@Override
	public void run() {
		final long beginningTime = System.currentTimeMillis();
		innerDoWork();
		this.timeConsume = System.currentTimeMillis() - beginningTime;
	}

	/**
	 * 负责具体执行测试逻辑
	 */
	private void innerDoWork() {
		//1.初始随机数发生器
		Random random = new Random();
		//2.循环指定次数
		int i = 0;
		int writeTimes = 0;
		while (i <= this.loopTimes) {
			Object key = random.nextInt(this.maxSize);
			if (this.container.get(key) == null) {
				this.container.put(key, key);
				writeTimes++;
			}
			i++;
		}
		System.out.println(Thread.currentThread().getName() + ":writeTimes:" + writeTimes);
	}
	/**
	 * 取操作耗时（毫秒）
	 * @return
	 */
	public long getTimeConsume() {
		return this.timeConsume;
	}
}
