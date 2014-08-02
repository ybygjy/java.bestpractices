package org.ybygjy.jcip.chap5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责实现{@link HashMap}与{@link ConcurrentHashMap}容器的性能测试
 * @author WangYanCheng
 * @version 2014年7月31日
 */
public class HashMapPerformanceTesting {
    /** 测试所需创建的线程数量*/
    private int threadNums;
    /** 各线程循环次数*/
    private int loopNums;
    /** 测试目标容器的读/写比率*/
    private float rwRatio;
    /** 被测试容器*/
    private final Map<Object, Object> targetContainer;
    /**
     * 构造方法
     * @param threadNums 线程数量
     * @param loopNums 循环次数
     * @param rwRatio 读/写比率
     * @param targetContainer 被测试容器
     */
    public HashMapPerformanceTesting(int threadNums, int loopNums, float rwRatio, Map<Object, Object> targetContainer) {
        this.threadNums = threadNums;
        this.loopNums = loopNums;
        this.rwRatio = rwRatio;
        this.targetContainer = targetContainer;
    }
    /**
     * 测试启动入口
     */
    public void doTest() {
        
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        //定义容器的抽象
        //定义闭锁#开始门
        //定义闭锁#结束门
        //定义初始入口，负责创建线程
    }
}
