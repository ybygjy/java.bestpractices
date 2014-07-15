package org.ybygjy.cache.memcache.spymemcached;

import java.util.Properties;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.KetamaConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.ybygjy.cache.CacheClient;
import org.ybygjy.cache.CacheClientFactory;

/**
 * 简单工厂，负责创建SpyMemcache实现的{@link CacheClient}
 * @author WangYanCheng
 * @version 2014-7-11
 */
public class SpyMemcacheClientFactory implements CacheClientFactory {
	/** 配置信息*/
	private Properties properties;
	/** 配置前缀*/
	private static final String PROP_PREFIX = "cache.distribution.";
	/** 缓存服务器地址*/
	private static final String PROP_SERVERS = PROP_PREFIX + "servers";
	/** 缓存工厂*/
	private static final String PROP_CONNECTION_FACTORY = PROP_PREFIX + "connectionFactory";
	/** 身份验证标识_USERNAME*/
	private static final String PROP_USERNAME = PROP_PREFIX + "username";
	/** 身份验证标识_PASSWORD*/
	private static final String PROP_PASSWORD = PROP_PREFIX + "password";
	/** 操作超时(毫秒)*/
	public static final String PROP_OPERATION_TIMEOUT = PROP_PREFIX + "operationTimeout";
	/** 后台进程模式*/
	public static final String PROP_DAEMON_MODE = PROP_PREFIX + "daemonMode";
	/** 工作队列长度*/
	public static final String PROP_OPERATION_QUEUE_LENGTH = PROP_PREFIX + "operationQueueLength";
	/** 缓冲区大小*/
	public static final String PROP_READ_BUFFER_SIZE = PROP_PREFIX + "readBufferSize";
	/** 指定Hash算法*/
	public static final String PROP_HASH_ALGORITHM = PROP_PREFIX + "hashAlgorithm";
	/**
	 * 构造方法
	 * @param properties {@link Properties}
	 */
	public SpyMemcacheClientFactory(Properties properties) {
		this.properties = properties;
	}
	/**
	 * {@inheritDoc}
	 */
	public CacheClient createCacheClient() throws Exception {
		ConnectionFactory connectionFactory = getConnectionFactory();
		MemcachedClient memcachedClient = new MemcachedClient(connectionFactory, AddrUtil.getAddresses(getServerList()));
		CacheClient rtnCache = new SpyMemcache(memcachedClient);
		return rtnCache;
	}
	/**
	 * 根据配置信息构造分布式缓存连接工厂实例
	 * @return connectionFactory {@link ConnectionFactory}
	 */
	private ConnectionFactory getConnectionFactory() {
		String connectionFactoryName = getConnectionFactoryName();
		if (connectionFactoryName.equals(DefaultConnectionFactory.class.getName())) {
			return buildDefaultConnectionFactory();
		}
		if (connectionFactoryName.equals(KetamaConnectionFactory.class.getName())) {
			return buildKetamaConnectionFactory();
		}
		if (connectionFactoryName.equals(BinaryConnectionFactory.class.getName())) {
			return buildBinaryConnectionFactory();
		}
		throw new IllegalArgumentException("未被支持的缓存工厂类型，Key:" + PROP_CONNECTION_FACTORY + "Value:" + getConnectionFactoryName());
	}
	/**
	 * 基于二进制连接协议的缓存服务构造工厂
	 * @return rtnConnFactory {@link BinaryConnectionFactory}
	 */
	private ConnectionFactory buildBinaryConnectionFactory() {
		BinaryConnectionFactory binaryConnectionFactory = new BinaryConnectionFactory(getOperationQueueLength(), getReadBufferSize(), getHashAlgorithm()){
			@Override
			public long getOperationTimeout() {
				return getOperationTimeoutMilli();
			}
			@Override
			public boolean isDaemon() {
				return isDaemonMode();
			}
			@Override
			public AuthDescriptor getAuthDescriptor() {
				return createAuthDescriptor();
			}
		};
		return binaryConnectionFactory;
	}
	/**
	 * 兼容一致性哈希算法的缓存服务构造工厂
	 * @return rtnFactory {@link KetamaConnectionFactory}
	 */
	private ConnectionFactory buildKetamaConnectionFactory() {
		KetamaConnectionFactory ketamaConnectionFactory = new KetamaConnectionFactory() {
			@Override
			public long getOperationTimeout() {
				return getOperationTimeoutMilli();
			}

			@Override
			public boolean isDaemon() {
				return isDaemonMode();
			}

			@Override
			public AuthDescriptor getAuthDescriptor() {
				return createAuthDescriptor();
			}
		};
		return ketamaConnectionFactory;
	}
	/**
	 * 默认缓存服务连接工厂
	 * @return rtnConnectionFactory {@link DefaultConnectionFactory}
	 */
	private DefaultConnectionFactory buildDefaultConnectionFactory() {
		DefaultConnectionFactory rtnConnectionFactory = new DefaultConnectionFactory(getOperationQueueLength(), getReadBufferSize(), getHashAlgorithm()){
			@Override
			public AuthDescriptor getAuthDescriptor() {
				return createAuthDescriptor();
			}
			@Override
			public long getOperationTimeout() {
				return getOperationTimeoutMilli();
			}

			@Override
			public boolean isDaemon() {
				return isDaemonMode();
			}
		};
		return rtnConnectionFactory;
	}
	/**
	 * 哈希算法
	 * @return hashAlgorithm
	 */
	public HashAlgorithm getHashAlgorithm() {
		String tmpValue = properties.getProperty(PROP_HASH_ALGORITHM);
		if (tmpValue == null) {
			return DefaultHashAlgorithm.KETAMA_HASH;
		}
		return Enum.valueOf(DefaultHashAlgorithm.class, tmpValue);
	}
	/**
	 * 缓存长度，该长度用在缓存客户端与缓存服务端通信时Socket的缓冲区大小
	 * @return buffSizeInt
	 */
	public int getReadBufferSize() {
		String tmpValue = properties.getProperty(PROP_READ_BUFFER_SIZE);
		return tmpValue == null ? DefaultConnectionFactory.DEFAULT_READ_BUFFER_SIZE : Integer.parseInt(tmpValue);
	}
	/**
	 * 工作队列长度
	 * @return rtnValueInt
	 */
	public int getOperationQueueLength() {
		String tmpValue = properties.getProperty(PROP_OPERATION_QUEUE_LENGTH);
		return tmpValue == null ? DefaultConnectionFactory.DEFAULT_OP_QUEUE_LEN : Integer.parseInt(tmpValue);
	}
	/**
	 * 配置服务身份验证
	 * @return authDescriptor {@link AuthDescriptor}
	 */
	protected AuthDescriptor createAuthDescriptor() {
		String userName = properties.getProperty(PROP_USERNAME);
		String password = properties.getProperty(PROP_PASSWORD);
		if (null == userName || null == password) {
			return null;
		}
		return new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(userName, password));
	}
	/**
	 * 缓存服务器列表
	 * @return rtnServerList
	 */
	public String getServerList() {
		return properties.getProperty(PROP_SERVERS);
	}
	/**
	 * 后台进程模式
	 * @return isDaemon true/false
	 */
	public boolean isDaemonMode() {
		String tmpValue = properties.getProperty(PROP_DAEMON_MODE);
		return null == tmpValue ? false : Boolean.parseBoolean(tmpValue);
	}
	/**
	 * 操作超时时间(Millisecond)
	 * @return rtnLong
	 */
	public long getOperationTimeoutMilli() {
		String tmpValue = properties.getProperty(PROP_OPERATION_TIMEOUT);
		return null == tmpValue ? DefaultConnectionFactory.DEFAULT_OPERATION_TIMEOUT : Long.parseLong(tmpValue);
	}
	/**
	 * 取配置的缓存连接工厂类名称，如果配置信息为空则取{@link DefaultConnectionFactory}
	 * @return connFactoryName
	 */
	public String getConnectionFactoryName() {
		String rtnValue = properties.getProperty(PROP_CONNECTION_FACTORY);
		if (rtnValue == null) {
			rtnValue = DefaultConnectionFactory.class.getSimpleName();
		}
		return rtnValue;
	}
}
