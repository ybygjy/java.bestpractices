package org.ybygjy.jcip.chap12;

import junit.framework.TestCase;

/**
 * 队列性能测试
 * @author WangYanCheng
 * @version 2014年11月6日
 */
public class BoundedBufferTest extends TestCase {
	/**
	 * 队列为空
	 */
	public void testIsEmptyWhenConstructed() {
		BoundedBuffer<Integer> boundedBuffObj = new BoundedBuffer<Integer>(10);
		assertTrue(boundedBuffObj.isEmpty());
	}
	/**
	 * 队列满
	 * @throws InterruptedException
	 */
	public void testIsFullAfterPuts() throws InterruptedException {
		BoundedBuffer<Integer> boundedBuffObj = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++) {
			boundedBuffObj.put(i);
		}
		assertTrue(boundedBuffObj.isFull());
		assertFalse(boundedBuffObj.isEmpty());
	}
	/**
	 * 测试阻塞行为以及对中断的响应性
	 * <p>从测试线程创建一个独立的线程，该线程负责阻塞测试的事情。</p>
	 * <p>主线程可利用创建的独立线程状态确定测试是否成功</p>
	 */
	public void testTakeBlocksWhenEmpty() {
		final BoundedBuffer<Integer> boundedBuffObj = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread(new Runnable(){
			public void run() {
				try {
					int unused = boundedBuffObj.take();
					fail("Fail=>" + unused);
				} catch (InterruptedException e) {
				}
			}
		}, "InnerBlockTestThread");
		try {
			taker.start();
			Thread.sleep(2000);
			taker.interrupt();
			taker.join(1000);
			assertFalse(taker.isAlive());
		} catch (Exception ex) {
			fail();
		}
	}
}
