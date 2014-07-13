package org.ybygjy.cache.memcache.spymemcached;

import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.cache.Cache;
import org.ybygjy.cache.CacheClientFactory;

public class SpyMemcacheClientFactoryTest {
	private Cache cacheInst;
	
	@Before
	public void beforeTest() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("cache.distributed.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		CacheClientFactory cacheClientFactory = new SpyMemcacheClientFactory(properties);
		try {
			cacheInst = cacheClientFactory.createCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCreateCache() {
		Assert.assertNotNull(cacheInst);
	}

}
