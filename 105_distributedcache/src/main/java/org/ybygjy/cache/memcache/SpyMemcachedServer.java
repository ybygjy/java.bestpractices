package org.ybygjy.cache.memcache;

/**
 * 定义SpyMemcached客户端
 * @author WangYanCheng
 * @version 2014-7-8
 */
public class SpyMemcachedServer {
	private String host;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("非法的商品号！【" + port + "】");
		}
		this.port = port;
	}
}
