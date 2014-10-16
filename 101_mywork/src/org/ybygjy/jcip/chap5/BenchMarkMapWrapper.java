package org.ybygjy.jcip.chap5;

/**
 * 定义参与测试容器的行为
 * @author WangYanCheng
 * @version 2014年10月15日
 */
public interface BenchMarkMapWrapper {
	/**
	 * 键值对存储
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value);
	/**
	 * 给定键取值
	 * @param key
	 * @return rtnObj
	 */
	public Object get(Object key);
	/**
	 * 清空容器
	 */
	public void clear();
	/**
	 * 取容品标识
	 * @return rtnStr
	 */
	public String getName();
}
