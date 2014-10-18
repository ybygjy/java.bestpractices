package org.ybygjy.jcip.chap5.bct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ybygjy.jcip.chap5.BenchmarkMapWrapper;

/**
 * 采用同步封装器确保容器的同步容器测试接口实现
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchmarkMapWrapperImpl4SynchroFactoryMap implements BenchmarkMapWrapper {
	private Map<Object, Object> hashMap;
	/**
	 * 构造函数
	 */
	public BenchmarkMapWrapperImpl4SynchroFactoryMap() {
		this.hashMap = Collections.synchronizedMap(new HashMap<Object, Object>());
	}
	@Override
	public void put(Object key, Object value) {
		this.hashMap.put(key, value);
	}

	@Override
	public Object get(Object key) {
		return this.hashMap.get(key);
	}

	@Override
	public void clear() {
		this.hashMap.clear();
	}

	@Override
	public String getName() {
		return "FactorySyncContainer";
	}
}
