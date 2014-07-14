package org.ybygjy.cache;

import java.util.Map;

/**
 * 抽象定义缓存基本功能
 * @author WangYanCheng
 * @version 2014-7-11
 */
public interface Cache {
    public Object get(String key);

    public Map<String, Object> getMulti(String... keys);

    public void set(String key, int cacheTimeSeconds, Object o);

    public void delete(String key);

    public long incr(String key, int factor, int startingValue);

    public void shutdown();
}
