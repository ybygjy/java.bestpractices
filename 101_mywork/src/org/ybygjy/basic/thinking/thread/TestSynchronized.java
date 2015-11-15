package org.ybygjy.basic.thinking.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 测试振亚聊的针对String变量的多线程争用场景
 * @author WangYanCheng
 * @version 2015年11月15日
 */
public class TestSynchronized {
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		TestSynchronized testSync = new TestSynchronized();
		String lock = "A";
		CountDownLatch latch = new CountDownLatch(1);
		testSync.new InnerClass("threadA", lock, latch);
		testSync.new InnerClass("threadB", lock, latch);
		//lock = "B";
		testSync.new InnerClass("threadC", lock, latch);
		testSync.new InnerClass("threadD", lock, latch);
		latch.countDown();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	class InnerClass extends Thread {
		private final String lock;
		private final CountDownLatch latch;
		private volatile int countStep;
		public InnerClass(String threadName, String lock, CountDownLatch latch) {
			super(threadName);
			this.lock = lock;
			this.latch = latch;
			start();
		}
		public void run() {
			while (true) {
				try {
					this.latch.await();
					synchronized(this.lock) {
						System.out.println(getName() + "#" + this.lock + "#" + this.countStep++);
					}
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
