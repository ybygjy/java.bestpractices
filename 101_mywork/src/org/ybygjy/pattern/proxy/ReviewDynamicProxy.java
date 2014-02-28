package org.ybygjy.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * 复习Java自带的动态代理
 * <p>1、调用处理器总会持有真实对象</p>
 * <p>2、调用处理器提供了真实对象行为的监听、扩展入口</p>
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class ReviewDynamicProxy {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            InvocationHandler ihInst = new TraceHandler(i);
            elements[i] = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, ihInst);
        }
        int key = new Random().nextInt(elements.length);
        int result = Arrays.binarySearch(elements, key);
        System.out.println(result);
    }
}
/**
 * 回调接收处理器
 * @author WangYanCheng
 * @version 2011-2-24
 */
class TraceHandler implements InvocationHandler {
    /**源目标对象*/
    private Object target;
    /**
     * Constructor
     * @param target 目标对象
     */
    public TraceHandler(Object target) {
        this.target = target;
    }
    /**
     * {@inheritDoc}
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null) {
            System.out.print(target + ":" + Arrays.toString(args) + "\n");
        }
        return method.invoke(target, args);
    }
}
