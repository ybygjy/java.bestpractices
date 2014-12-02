package org.ybygjy.memcached;

import java.util.Date;
import java.util.Map;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * MemCached最佳实践
 * @author WangYanCheng
 * @version 2011-5-6
 */
public class MemCachedMgr {
    /** MemCached服务器地址 */
    private String[] serverAddr = {"172.16.0.75:11211", "localhost:11211"};
    /** 权重 */
    private Integer[] weights = {2, 3};
    /** MemCached连接 */
    private MemCachedClient mccInst;
    /** 连接池 */
    private SockIOPool sockPool;
    /** 池默认连接数量 */
    private int initConn = 5;
    /** 池最小连接数量 */
    private int minConn = 5;
    /** 池最大连接数量 */
    private int maxConn = 100;
    /** 池连接最长空闲时间(耗秒) */
    private long maxIdle = 1000 * 60 * 60 * 6;
    /** 池的维护线程轮循时间 */
    private int maintSleep = 30;
    /** 网络字节缓存标记，这里涉及到一个名叫Nagle的算法 */
    private boolean nagle = false;
    /** 网络超时时间 */
    private int socketTo = 3000;
    /** 网络连接超时时间 */
    private int connectTo = 0;

    /**
     * 构造方法，提供初始化MemCached环境参数
     * @param serverAddr MemCached服务器地址组
     * @param weights MemCached服务器对应的权重配置
     */
    public MemCachedMgr(String[] serverAddr, Integer[] weights) {
        this.serverAddr = serverAddr == null ? this.serverAddr : serverAddr;
        this.weights = weights;
        initializeSocketPool();
        mccInst = new MemCachedClient();
    }

    /**
     * 初始化连接池环境
     */
    private void initializeSocketPool() {
        sockPool = SockIOPool.getInstance();
        sockPool.setServers(serverAddr);
        sockPool.setWeights(weights);
        sockPool.setInitConn(initConn);
        sockPool.setMinConn(minConn);
        sockPool.setMaxConn(maxConn);
        sockPool.setMaxIdle(maxIdle);
        sockPool.setMaintSleep(maintSleep);
        sockPool.setNagle(nagle);
        sockPool.setSocketTO(socketTo);
        sockPool.setSocketConnectTO(connectTo);
        sockPool.initialize();
    }

    /**
     * 删除MemCached中与指定键匹配的项
     * @param key 指定键
     * @return 删除结果 true(成功删除)/false(失败)
     */
    public boolean delete(String key) {
        return mccInst.delete(key);
    }

    /**
     * 从MemCached缓存系统中取数据
     * @param key 键
     * @return value/null
     */
    public Object get(String key) {
        return mccInst.get(key);
    }

    /**
     * 批量从MemCached中取出一组数据
     * @param keys 指定键，多个用<strong>逗号</strong>分割
     * @return dataArray 数据集
     */
    public Map<String, Object> getMulti(String... keys) {
        if (null == keys || keys.length == 0) {
            return null;
        }
        return mccInst.getMulti(keys);
    }

    /**
     * 检测给定键是否已在MemCached中
     * @param key 指定键
     * @return true/false
     */
    public boolean isContain(String key) {
        return mccInst.keyExists(key);
    }

    /**
     * 缓存数据
     * <p>
     * <strong>注意：</strong>该方法会覆盖掉缓存中相同key的数据
     * </p>
     * @param key 键
     * @param value 值
     * @return 存储结果：true(成功)/false(失败)
     */
    public boolean set(String key, Object value) {
        return set(key, value, true);
    }

    /**
     * 缓存数据
     * @param key 键
     * @param value 值
     * @param overwrite 覆盖标记
     * @return 存储结果：true(成功)/false(失败)
     */
    public boolean set(String key, Object value, boolean overwrite) {
        if (!overwrite && isContain(key)) {
            return false;
        }
        return mccInst.set(key, value);
    }

    /**
     * 缓存数据
     * @param key 键
     * @param value 值
     * @return 存储结果：true(成功)/false(失败)
     */
    public boolean add(String key, Object value) {
        return mccInst.add(key, value);
    }

    /**
     * 缓存数据
     * @param key 键
     * @param value 值
     * @param expiry 失效时间
     * @return 存储结果：true(成功)/false(失败)
     */
    public boolean add(String key, Object value, Date expiry) {
        return mccInst.add(key, value, expiry);
    }

    /**
     * 减去键对应的数据项的值，如果键对应的数据项不存在则自动数据项
     * @param key 指定键
     * @param value 递减量
     * @return 数据项值
     */
    public long addOrDecr(String key, long value) {
        long tmpV = mccInst.addOrDecr(key, value);
        return tmpV;
    }

    /**
     * 增加键对应的数据项的值，如果键对应的数据项不存在则自动数据项
     * @param key 指定键
     * @param value 增量
     * @return 数据项值
     */
    public long addOrIncr(String key, long value) {
        return mccInst.addOrIncr(key, value);
    }

    /**
     * 键对应的数据项的值加上指定数值
     * <p>
     * <strong>注意：</strong>如果键值类型不符合要求则自动转为0，请慎重！
     * </p>
     * @param key 指定键
     * @param value 指定数值
     * @return 数据项的值/-1(表示操作未成功)
     */
    public long incr(String key, long value) {
        return mccInst.incr(key, value);
    }

    /**
     * 键对应的数据项的值减去指定数值
     * <p>
     * <strong>注意：</strong>如果键值类型不符合要求则自动转为0，请慎重！
     * </p>
     * @param key 指定键
     * @param value 指定数值
     * @return 数据项的值/-1(表示操作未成功)
     */
    public long decr(String key, long value) {
        return mccInst.decr(key, value);
    }

    /**
     * 刷新指定MemCached服务器的缓存数据
     * <p>
     * <strong>注意：</strong>刷新即清理相应服务器上的数据，请慎重！
     * </p>
     * @param serverAddr MemCached服务器地址
     * @return 处理结果 true(成功)/false(失败)
     */
    public boolean flushAll(String... serverAddr) {
        if (null == serverAddr || serverAddr.length == 0) {
            return false;
        }
        return mccInst.flushAll(serverAddr);
    }

    /**
     * 构建测试数据集
     * @param size 测试数据集大小
     * @return testAttr 测试数据集
     */
    public static String[][] getTestData(int size) {
        String[][] testAttr = new String[size][2];
        for (int i = 0; i < size; i++) {
            testAttr[i][0] = "KEY_" + i;
            testAttr[i][1] = "VALUE_" + Math.random();
        }
        return testAttr;
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        MemCachedMgr mcmInst = new MemCachedMgr(null, null);
        String[][] dataStr = mcmInst.getTestData(10);
        for (int i = dataStr.length - 1; i >= 0; i--) {
            System.out.println(mcmInst.add(dataStr[i][0], dataStr[i][1]));
        }
    }
}
