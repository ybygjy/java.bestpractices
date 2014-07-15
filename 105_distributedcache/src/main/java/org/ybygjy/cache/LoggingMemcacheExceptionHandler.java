package org.ybygjy.cache;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 集成日志处理模块记录异常信息
 * @author WangYanCheng
 * @version 2014-7-11
 */
public class LoggingMemcacheExceptionHandler implements CacheClientExceptionHandler {
	private static Logger logger = Logger.getLogger(LoggingMemcacheExceptionHandler.class.getName());
	public void handleErrorOnGet(String key, Exception e) {
		logger.log(Level.WARNING, "缓存_取数据异常{key:" + key + "}", e.getCause());
	}

	public void handleErrorOnSet(String key, int cacheTimeSeconds, Object o, Exception e) {
		logger.log(Level.WARNING, "缓存_存数据异常{key:" + key + "}", e.getCause());
	}

	public void handleErrorOnDelete(String key, Exception e) {
		logger.log(Level.WARNING, "缓存_删数据异常{key:" + key + "}", e.getCause());
	}

	public void handleErrorOnIncr(String key, int factor, int startingValue, Exception e) {
		logger.log(Level.WARNING, "缓存_递增异常{key:" + key + ",factor:" + factor + "}", e.getCause());
	}

}
