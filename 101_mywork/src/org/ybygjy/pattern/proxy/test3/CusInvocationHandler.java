package org.ybygjy.pattern.proxy.test3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * InvocationHandler
 * @author WangYanCheng
 * @version 2009-12-31
 */
public class CusInvocationHandler implements InvocationHandler {
    /**daoInst*/
    private Object oInst = null;
    /**
     * doGetProxyInst
     * @param obj obj
     * @return objInst
     */
    public Object doGetProxyInst(Object obj) {
        this.oInst = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), oInst.getClass().getInterfaces(), this);
    }
    /**
     * {@inheritDoc}
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before Invoke..");
        Object resultObj = method.invoke(oInst, args);
        System.out.println("After Invoke..");
        return resultObj;
    }
}
