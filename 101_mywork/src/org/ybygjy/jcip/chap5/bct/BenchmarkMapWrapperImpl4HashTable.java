package org.ybygjy.jcip.chap5.bct;

import java.util.Hashtable;
import java.util.Map;

import org.ybygjy.jcip.chap5.BenchmarkMapWrapper;

/**
 * 容器测试接口HashTable实现
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public class BenchmarkMapWrapperImpl4HashTable implements BenchmarkMapWrapper {
    private final Map<Object, Object> map;

    /**
     * Constructor
     */
    public BenchmarkMapWrapperImpl4HashTable() {
        map = new Hashtable<Object, Object>();
    }
    @Override
    public void clear() {
        map.clear();
    }
    @Override
    public Object get(final Object key) {
        return map.get(key);
    }
    @Override
    public void put(final Object key, final Object value) {
        map.put(key, value);
    }
    @Override
    public String getName() {
        return "HashTable Container";
    }
}
