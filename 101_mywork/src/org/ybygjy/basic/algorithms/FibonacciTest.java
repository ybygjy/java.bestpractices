package org.ybygjy.basic.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证两种递归实现Fibonacci的方法
 * <p>
 * 1、直接使用堆栈
 * <p>
 * 2、加入缓存的概念
 * @author WangYanCheng
 * @version 2012-9-5
 */
public class FibonacciTest {
    /** 缓存 */
    private Map<Integer, Long> cacheMap = new HashMap<Integer, Long>();

    /**
     * 第一种方式
     * @param i 要计算的fibonacci数
     * @return rtnNum fibonacci值
     */
    private long fibonacciOne(int i) {
        if (i == 0 || i == 1) {
            return i;
        }
        return fibonacciOne(i - 1) + fibonacciOne(i - 2);
    }

    /**
     * 第二种方式_使用缓存
     * @param i 要计算的Fibonacci数
     * @return rtnNum fibonacci值
     */
    private long fibonacciTwo(int i) {
        if (cacheMap.containsKey(i)) {
            return cacheMap.get(i);
        }
        if (i == 0 || i == 1) {
            cacheMap.put(i, (long) i);
        } else {
            cacheMap.put(i, fibonacciTwo(i - 1) + fibonacciTwo(i - 2));
        }
        return cacheMap.get(i);
    }

    /**
     * 测试入口
     * @param n fibonacci参数
     */
    public void doWork(int n) {
        long beginTime = System.currentTimeMillis();
        long rtnValue = this.fibonacciOne(n);
        long endTime = System.currentTimeMillis();
        double diff = endTime - beginTime;
        System.out.println("第一种，耗时：".concat(String.valueOf(diff / (1000*60))).concat("秒，值：".concat(String.valueOf(rtnValue))));
        beginTime = System.currentTimeMillis();
        rtnValue = this.fibonacciTwo(n);
        endTime = System.currentTimeMillis();
        diff = endTime - beginTime;
        System.out.println("第二种，耗时：".concat(String.valueOf(diff / (1000*60))).concat("秒，值：".concat(String.valueOf(rtnValue))));
    }

    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args) {
        FibonacciTest ftInst = new FibonacciTest();
        ftInst.doWork(50);
    }
}
