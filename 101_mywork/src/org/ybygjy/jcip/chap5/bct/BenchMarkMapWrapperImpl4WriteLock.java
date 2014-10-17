package org.ybygjy.jcip.chap5.bct;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.ybygjy.jcip.chap5.BenchMarkMapWrapper;

/**
 * 测试容器实现#
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchMarkMapWrapperImpl4WriteLock implements BenchMarkMapWrapper {
	private Map<Object, Object> container;
	private Lock lock;
	/**
	 * 构造函数
	 */
	public BenchMarkMapWrapperImpl4WriteLock() {
		this.container = new HashMap<Object, Object>();
		this.lock = new ReentrantLock();
	}
	@Override
	public void put(Object key, Object value) {
		this.lock.lock();
		try {
			this.container.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	@Override
	public Object get(Object key) {
		return this.container.get(key);
	}
	@Override
	public void clear() {
		this.lock.lock();
		try {
			this.container.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.lock.unlock();
		}
	}
	@Override
	public String getName() {
		return "OnlyWriteLockContainer";
	}
}
