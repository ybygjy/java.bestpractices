package org.ybygjy.jcip.chap4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 锁在哪里的问题。
 * <p>1、此代码验证客户端正确和不正确加锁的场景</p>
 * <p>2、可以观察到addIfAbsent并未符合我们的要求，从结果可以看到重复值</p>
 * <p>
 * 3、操作步骤
 * 3.1、声明一个List的扩展类，该类负责扩展List的功能。
 * 3.2、在扩展类中新加入addIfAbsent函数，该函数错误的使用了锁，导致扩展的list新特性不具有线程安全性
 * 3.3、创建测试线程类，负责模拟测试环境
 * 3.4、创建多个线程，并发读取和写入数据到list的扩展实例中
 * 3.5、打印list扩展实例数据，观察测试结果
 * <p>
 * 4、小结
 * 4.1、通过添加原子操作来扩展现存类非常脆弱，因为这会将类的加锁代码分布到多个类中。
 * 4.2、客户端加锁更加脆弱，因为它将原始类的加锁代码放到与原始类完全无关的其它类中。
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
		ListHelper calInst = new ListHelper();
		//声明6个线程
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
		private ListHelper calInst;
		/**
		 * 构造函数
		 * @param threadName 线程名称
		 * @param calInst {@link ListHelper}
		 */
		public TestThread(String threadName, ListHelper calInst) {
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
/**
 * 声明List类的扩展类，该类负责扩展List的功能，但是该类因为使用了错误的锁，所以不具有线程安全性，该类仅仅带来了同步的假象。
 * @author WangYanCheng
 * @version 2014年8月13日
 */
class ListHelper {
	/**
	 * 1.声明支持并发操作的list变量
	 * 2.注意List在实现客户端加锁或外部加锁时使用同一个锁。
	 */
	public List<Object> list = (List<Object>) Collections.synchronizedList(new ArrayList<Object>());
	/**
	 * 普通
	 * @param obj
	 */
	public void add(Object obj) {
		synchronized(list) {
			if (list.contains(obj)) {
				return;
			}
			list.add(obj);
		}
	}
	/**
	 * 如果当前list中不存在该元素则加入，否则不做任何操作
	 * <p>无论list使用哪一个锁来保护它的状态，我们可以确定的是，这个锁并不是</p>
	 * @param obj
	 * @return rtnFlag {true:存在未添加,false:不存在已添加}
	 */
	public boolean addIfAbsent(Object obj) {
		synchronized(this) {
			boolean isAbsent = !this.list.contains(obj);
			String tmpStr = "T_" + obj;
			if (!isAbsent) {
				this.list.add(tmpStr);
			}
			return isAbsent;
		}
	}
	public String toString() {
		return list.toString();
	}
}