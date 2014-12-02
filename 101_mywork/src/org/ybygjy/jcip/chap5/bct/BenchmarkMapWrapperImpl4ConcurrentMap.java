package org.ybygjy.jcip.chap5.bct;

import java.util.concurrent.ConcurrentHashMap;

import org.ybygjy.jcip.chap5.BenchmarkMapWrapper;

/**
 * 测试容器实现#并发容器实现
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchmarkMapWrapperImpl4ConcurrentMap implements BenchmarkMapWrapper {
	private ConcurrentHashMap<Object, Object> container;
	/**
	 * Constructor
	 */
	public BenchmarkMapWrapperImpl4ConcurrentMap() {
		this.container = new ConcurrentHashMap<Object, Object>();
	}
	@Override
	public void put(Object key, Object value) {
		this.container.put(key, value);
	}

	@Override
	public Object get(Object key) {
		return this.container.get(key);
	}

	@Override
	public void clear() {
		this.container.clear();
	}

	@Override
	public String getName() {
		return "ConcurrentMapContainer";
	}
}
