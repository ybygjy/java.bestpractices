package org.ybygjy.cache;

/**
 * Cache“Ï≥£
 * @author WangYanCheng
 * @version 2014-7-11
 */
public interface CacheClientExceptionHandler {
    void handleErrorOnGet(String key, Exception e);

    void handleErrorOnSet(String key, int cacheTimeSeconds, Object o, Exception e);

    void handleErrorOnDelete(String key, Exception e);

    void handleErrorOnIncr(String key, int factor, int startingValue, Exception e);
}
