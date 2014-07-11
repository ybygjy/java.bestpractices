package org.ybygjy.cache;

/**
 * 工厂接口用于抽象定义创建{@link Cache}行为
 * @author WangYanCheng
 * @version 2014-7-11
 */
public interface CacheClientFactory {
	public Cache createCache() throws Exception;
}
