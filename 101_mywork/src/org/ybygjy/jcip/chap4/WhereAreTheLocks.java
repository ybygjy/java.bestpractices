package org.ybygjy.jcip.chap4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 锁在哪里的问题。
 * <p>1、此代码验证客户端正确和不正确加锁的场景</p>
 * <p>2、可以观察到addIfAbsent并未复合我们的要求，比结果可以看到重复值</p>
 * @author WangYanCheng
 * @version 2014年8月13日
 */
public class WhereAreTheLocks {
	private static final CountDownLatch latch = new CountDownLatch(6);
	/**
	 * 测试执行入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		CorrectAndAnCorrectLocked calInst = new CorrectAndAnCorrectLocked();
		for (int i = 0; i < 6; i++) {
			new TestThread("WhereAreTheLocks_" + i, calInst).start();
		}
	}
	/**
	 * 测试线程类
	 * @author WangYanCheng
	 * @version 2014年8月13日
	 */
	static class TestThread extends Thread {
		private CorrectAndAnCorrectLocked calInst;
		/**
		 * 构造函数
		 * @param threadName 线程名称
		 * @param calInst {@link CorrectAndAnCorrectLocked}
		 */
		public TestThread(String threadName, CorrectAndAnCorrectLocked calInst) {
			super(threadName);
			this.calInst = calInst;
		}
		@Override
		public void run() {
			for (int i = 0; i < 21; i++) {
				long v = (long) (Math.random() * 10);
				if ((long)(Math.random() * 10) % 2 == 0) {
					calInst.add(v);
				} else {
					calInst.addIfAbsent(v);
				}
				try {
					sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			latch.countDown();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + ">>" + calInst.toString());
		}
	}
}
class CorrectAndAnCorrectLocked {
	/** 1.声明支持并发操作的list变量*/
	//TODO 锁在哪里？
	public List<Object> list = (List<Object>) Collections.synchronizedList(new ArrayList<Object>());
	/**
	 * 普通
	 * @param obj
	 */
	public void add(Object obj) {
		//TODO 锁在哪里？
		synchronized(list) {
			list.add(obj);
		}
	}
	/**
	 * 如果当前list中不存在该元素则加入，否则不做任何操作
	 * @param obj
	 * @return rtnFlag {true:存在未添加,false:不存在已添加}
	 */
	public boolean addIfAbsent(Object obj) {
		//TODO 锁在哪里？
		synchronized(this) {
			boolean isAbsent = !this.list.contains(obj);
			if (!isAbsent) {
				this.list.add("T_" + obj);
			}
			return isAbsent;
		}
	}
	public String toString() {
		return list.toString();
	}
}