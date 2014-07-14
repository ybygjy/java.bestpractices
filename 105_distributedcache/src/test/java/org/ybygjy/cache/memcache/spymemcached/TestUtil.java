package org.ybygjy.cache.memcache.spymemcached;

import java.io.IOException;
import java.util.Properties;

/**
 * 辅助测试
 * <p>1、装载{@link Properties}</p>
 * @author WangYanCheng
 * @version 2014-7-14
 */
public class TestUtil {
	private String configFileName = "cache.distributed.properties";
	/**
	 * 取指定配置文件的管理实例
	 * @param configFileLocation 配置文件地址
	 * @return properties {@link Properties}
	 */
	public Properties getProperties(String configFileLocation) {
		Properties properties = new Properties();
		try {
			properties.load(TestUtil.class.getClassLoader().getResourceAsStream(configFileLocation));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("加载");
		}
		return properties;
	}
	/**
	 * 取默认配置信息管理实例
	 * @return properties {@link Properties}
	 */
	public Properties getProperties() {
		return this.getProperties(configFileName);
	}
}
