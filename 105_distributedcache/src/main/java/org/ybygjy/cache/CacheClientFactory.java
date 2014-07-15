package org.ybygjy.cache;

/**
 * 工厂接口用于抽象定义创建{@link CacheClient}行为
 * @author WangYanCheng
 * @version 2014-7-11
 */
public interface CacheClientFactory {
	/**
	 * 创建{@link CacheClient}实体
	 * @return cacheClient {@link CacheClient}
	 * @throws Exception
	 */
	public CacheClient createCacheClient() throws Exception;
}
