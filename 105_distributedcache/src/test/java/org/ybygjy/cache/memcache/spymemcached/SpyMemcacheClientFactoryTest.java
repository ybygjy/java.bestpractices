package org.ybygjy.cache.memcache.spymemcached;

import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.cache.CacheClient;
import org.ybygjy.cache.CacheClientFactory;

/**
 * 单元测试#SpyMemcacheClientFactory
 * @author WangYanCheng
 * @version 2014-7-14
 */
public class SpyMemcacheClientFactoryTest {
	/** 缓存配置文件*/
	private String confFile = "cache.distributed.properties";
	private CacheClient cacheInst;
	
	@Before
	public void beforeTest() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(confFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		CacheClientFactory cacheClientFactory = new SpyMemcacheClientFactory(properties);
		try {
			cacheInst = cacheClientFactory.createCacheClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCreateCache() {
		Assert.assertNotNull(cacheInst);
	}
}
