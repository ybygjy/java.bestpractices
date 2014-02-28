package org.ybygjy.xml.dom4j;

import org.dom4j.Element;

/**
 * 利与实现AOP机制的接口定义,负责特定xml文档操作的监听
 * @author WangYanCheng
 * @version 2010-8-3
 */
public interface AOPListener {
    /**
     * 处理文档解析之Element元素
     * @param elemInst elemInst
     */
    void afterParseElement(Element elemInst);
    /**
     * 处理文档解析之Element元素
     * @param elemInst elemInst
     */
    void beforeParseElement(Element elemInst);
}
