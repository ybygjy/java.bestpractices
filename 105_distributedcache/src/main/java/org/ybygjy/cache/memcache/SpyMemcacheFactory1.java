package org.ybygjy.cache.memcache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

/**
 * Memcached客户端公共接口适配
 * <p>类似缓存、日志、调度器、消息队列这类基础性质的服务应放到服务器启动时直接加载并初始化，无须等到真正业务逻辑初始使用时再进行初始化。</p>
 * @author WangYanCheng
 * @version 2014-7-8
 */
public class SpyMemcacheFactory1 {
	public static int DEFAULT_TIMEOUT = 5000;
	public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
/*
	hibernate.memcached.servers=10.1.7.199:12000 10.1.7.199:13000
	search.memcached.servers=10.1.200.139:12000,10.1.200.139:13000,10.1.200.139:14000,10.1.200.139:15000
*/
	/** 缓存服务集群*/
	private String memcachedServers;
	/** SpyMemcached客户端操作API*/
	private MemcachedClient memcachedClient;
	/**
	 * Constructor
	 * @param memcachedServers 缓存服务器集群
	 */
	public SpyMemcacheFactory1(String memcachedServers) {
		this.memcachedServers = memcachedServers;
	}
	/**
	 * 创建连接实例化MemcachedClient
	 * @throws IOException
	 */
	public void init() throws IOException {
		if (memcachedClient != null) {
			return;
		}
		ConnectionFactoryBuilder connFactory = new ConnectionFactoryBuilder();
		connFactory.setFailureMode(FailureMode.Redistribute);
		connFactory.setDaemon(true);
		connFactory.setProtocol(Protocol.TEXT);
		connFactory.setLocatorType(Locator.CONSISTENT);
		connFactory.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
		connFactory.setOpTimeout(DEFAULT_TIMEOUT);
		memcachedClient = new MemcachedClient(connFactory.build(), AddrUtil.getAddresses(this.memcachedServers));
	}
	/**
	 * 销毁MemcachedClient
	 */
	public void disconn() {
		if (memcachedClient == null) {
			return;
		}
		memcachedClient.shutdown();
	}
	/**
	 * 取{@link MemcachedClient}客户端
	 * @return memcachedClient
	 */
	public MemcachedClient getMemcachedClient() {
		return this.memcachedClient;
	}
}
