package org.ybygjy.pattern.proxy.tracer;

import java.awt.Component;
import java.awt.Container;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 监测事件信息，并输出给相应组件。这是Java核心技术卷I调试技术篇
 * @author WangYanCheng
 * @version 2011-1-26
 */
public class EventTracer {
    /** Dynamic Proxy */
    private InvocationHandler handler;

    /**
     * Constructor
     */
    public EventTracer() {
        handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method + ":" + args[0]);
                return null;
            }
        };
    }
    /**
     * 利用JavaBean#Introspector机制解析提取组件结构，将提取的结构信息与Proxy机制绑定
     * @param c 组件实例
     */
    public void add(Component c) {
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(c.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        EventSetDescriptor[] eventSets = info.getEventSetDescriptors();
        for (EventSetDescriptor eventSet : eventSets) {
            addListener(c, eventSet);
        }
        if (c instanceof Container) {
            for (Component comp : ((Container) c).getComponents()) {
                add(comp);
            }
        }
    }
    /**
     * 负责将JavaBean提取的结构实例利用Proxy机制进行组装从而起到监听、回调作用
     * @param c 监测事件组件
     * @param eventSet 事件描述实例，存储着事件接口信息、事件组件绑定事件内容等
     */
    public void addListener(Component c, EventSetDescriptor eventSet) {
        Object proxy = Proxy.newProxyInstance(null, new Class[] {eventSet.getListenerType()}, handler);
        Method addListenerMethod = eventSet.getAddListenerMethod();
        try {
            addListenerMethod.invoke(c, proxy);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
