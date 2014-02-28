package org.ybygjy.pattern.proxy.test2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * VectorProxy
 * @author WangYanCheng
 * @version 2009-12-22
 */
public class VectorProxy implements InvocationHandler {
    /**proxy obj*/
    private Object proxyObj = null;
    /**
     * Constructor
     * @param proxyObj proxyObj
     */
    public VectorProxy(Object proxyObj) {
        this.proxyObj = proxyObj;
    }
    /**
     * get a proxy class instance
     * @param obj obj
     * @return objInst
     */
    public static Object factory(Object obj) {
        Class clsInst = obj.getClass();
        return Proxy.newProxyInstance(clsInst.getClassLoader(), clsInst.getInterfaces(), new VectorProxy(obj));
    }
    /**
     * {@inheritDoc}
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.matches("^add.*")) {
            if (args != null) {
                for (Object tmpObj : args) {
                    System.out.println(tmpObj);
                }
                args[0] = "王延成";
            }
        }
        Object obj = method.invoke(proxyObj, args);
        return obj;
    }
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        List listInst = (List) factory(new ArrayList());
        listInst.add("Hello Mr.Wang.");
        listInst.add("Achieve something.");
        System.out.println(listInst);
    }
}
