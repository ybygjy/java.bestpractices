package org.ybygjy.cache.memcache;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

/**
 * Memcached�ͻ��˹����ӿ�����
 * <p>���ƻ��桢��־������������Ϣ��������������ʵķ���Ӧ�ŵ�����������ʱֱ�Ӽ��ز���ʼ��������ȵ�����ҵ���߼���ʼʹ��ʱ�ٽ��г�ʼ����</p>
 * @author WangYanCheng
 * @version 2014-7-8
 */
public class SpyMemcacheFactory {
	/** �������Ⱥ*/
	private String memcachedServers;
	/** SpyMemcached�ͻ��˲���API*/
	private MemcachedClient memcachedClient;
	/**
	 * Constructor
	 * @param memcachedServers �����������Ⱥ
	 */
	public SpyMemcacheFactory(String memcachedServers) {
		this.memcachedServers = memcachedServers;
	}
	/**
	 * ��������ʵ����MemcachedClient
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
		connFactory.setOpTimeout(1000);
		memcachedClient = new MemcachedClient(connFactory.build(), AddrUtil.getAddresses(this.memcachedServers));
	}
	/**
	 * ����MemcachedClient
	 */
	public void disconn() {
		if (memcachedClient == null) {
			return;
		}
		memcachedClient.shutdown();
	}
	/**
	 * ȡ{@link MemcachedClient}�ͻ���
	 * @return memcachedClient
	 */
	public MemcachedClient getMemcachedClient() {
		return this.memcachedClient;
	}
}