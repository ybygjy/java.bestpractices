package org.ybygjy.cache.memcache.spymemcached;

import java.util.Properties;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.DefaultConnectionFactory;

import org.ybygjy.cache.Cache;
import org.ybygjy.cache.CacheClientFactory;

/**
 * 简单工厂，负责创建SpyMemcache实现的{@link Cache}
 * @author WangYanCheng
 * @version 2014-7-11
 */
public class SpyMemcacheClientFactory implements CacheClientFactory {
	private Properties properties;
	public SpyMemcacheClientFactory(Properties properties) {
		this.properties = properties;
	}
	public Cache createCache() throws Exception {
		ConnectionFactory connectionFactory = getConnectionFactory();
		return null;
	}
	private ConnectionFactory getConnectionFactory() {
		if (connectionFactoryNameEquals(DefaultConnectionFactory.class)) {
			return buildDefaultConnectionFactory();
		}
		return null;
	}

}
