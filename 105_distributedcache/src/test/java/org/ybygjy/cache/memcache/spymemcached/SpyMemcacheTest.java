package org.ybygjy.cache.memcache.spymemcached;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ybygjy.cache.Cache;
import org.ybygjy.cache.CacheClientFactory;

/**
 * SpyMemcache缓存客户端测试
 * @author WangYanCheng
 * @version 2014-7-14
 */
public class SpyMemcacheTest {
	private Cache cacheInst = null;
	/**Key前缀*/
	private static String CACHE_PREFIX = "spyclient.test";
	@Before
	public void setUp() throws Exception {
		TestUtil testUtil = new TestUtil();
		CacheClientFactory cacheClientFactory = new SpyMemcacheClientFactory(testUtil.getProperties());
		cacheInst = cacheClientFactory.createCache();
	}

	@After
	public void tearDown() throws Exception {
		if (null != cacheInst) {
			cacheInst.shutdown();
		}
	}

	/**
	 * 解决测试单元顺序执行的问题
	 */
	@Test
	public void doTest() {
		testGetMemcachedClient();
		testSet();
		testGet();
		testGetMulti();
		testIncr();
		testDelete();
		testShutdown();
	}
	@Test
	@Ignore
	public void testSet() {
		printObj("Starting to setValue。。。");
		for (int i = 0; i < 100; i++) {
			String key = buildKey(i);
			cacheInst.set(key, (int)(Math.random() * 10), String.valueOf(i));
		}
		for (int i = 100; i < 200; i++) {
			Map<String, String> dataVal = new HashMap<String, String>();
			for (int j = 0; j < 5; j++) {
				dataVal.put("MU" + j, String.valueOf(Math.random()));
			}
			String key = buildKey(i);
			cacheInst.set(key, (int)(Math.random()), dataVal);
		}
		printObj("Ending to setValue。。。");
	}
	@Test
	@Ignore
	public void testGet() {
		for (int i = 0; i < 100; i++) {
			Object obj = cacheInst.get(buildKey(i));
			if (null == obj) {
				continue;
			}
			printObj(obj);
		}
	}

	@Test
	@Ignore
	public void testGetMulti() {
		for (int i = 100; i < 200; i++) {
			Object obj = cacheInst.get(buildKey(i));
			printObj(obj);
		}
	}


	@Test
	@Ignore
	public void testDelete() {
		for (int i = 0; i < 200; i++) {
			String key = buildKey(i);
			cacheInst.delete(key);
		}
	}

	@Test
	@Ignore
	public void testIncr() {
		for (int i = 0; i < 100; i++) {
			String key = buildKey(i);
			String oldVal = (String.valueOf(cacheInst.get(key)));
			if ("null".equals(oldVal)) {
				continue;
			}
			long rtnValue = cacheInst.incr(key, 1, Integer.parseInt(oldVal));
			printObj("testIncr=>" + key + ":" + 1 + ":" + oldVal + ":" + rtnValue);
		}
	}

	@Test
	@Ignore
	public void testShutdown() {
		cacheInst.shutdown();
	}

	@Test
	@Ignore
	public void testGetMemcachedClient() {
		Assert.assertNotNull(cacheInst);
	}

	private void printObj(Object obj) {
		System.out.println(obj.getClass().getName() + "：" + obj.toString());
	}
	private String buildKey(int f) {
		return CACHE_PREFIX + "(" + f + ")";
	}
}
