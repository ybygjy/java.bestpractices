package org.ybygjy.concurrency;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 学习{@link ConcurrentNavigableMap}
 * @author WangYanCheng
 * @version 2014年10月22日
 */
public class ConcurrentNavigableMapTest {
	private ConcurrentNavigableMap<String, String> concurrentNavigableMap;
	public ConcurrentNavigableMapTest() {
		this.concurrentNavigableMap = new ConcurrentSkipListMap<String, String>();
	}
	/**
	 * 测试入口
	 */
	public void doTest() {
		for (int i = 0; i < 2; i++) {
			new WriteThread("R_" + i, this.concurrentNavigableMap).start();
		}
		for (int i = 0; i < 1; i++) {
			new ReadThread("W_" + i, this.concurrentNavigableMap).start();
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ConcurrentNavigableMapTest().doTest();
	}
	class WriteThread extends Thread {
		private ConcurrentNavigableMap<String, String> concurrentNavigableMap;
		public WriteThread(String threadName, ConcurrentNavigableMap<String, String> concurrentNavigableMap) {
			super(threadName);
			this.concurrentNavigableMap = concurrentNavigableMap;
		}
		public void run() {
			while (true) {
				for (int i = 1; i < 6; i++) {
					String key = String.valueOf(getName() + "_" + i);
					this.concurrentNavigableMap.put(key, key);
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class ReadThread extends Thread {
		private ConcurrentNavigableMap<String, String> concurrentNavigableMap;
		public ReadThread(String threadName, ConcurrentNavigableMap<String, String> concurrentNavigableMap) {
			super(threadName);
			this.concurrentNavigableMap = concurrentNavigableMap;
		}
		public void run() {
			while (true) {
				Iterator<Entry<String, String>> iterator = this.concurrentNavigableMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();
					System.out.print(entry.getKey() + ":" + entry.getValue());
					System.out.print(", ");
				}
				System.out.println();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
