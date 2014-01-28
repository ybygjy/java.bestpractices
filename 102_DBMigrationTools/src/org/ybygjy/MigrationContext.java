package org.ybygjy;


/**
 * 引入上下文概念，解耦组件之间的依赖
 * @author WangYanCheng
 * @version 2012-10-17
 */
public interface MigrationContext {
    /**
     * 取上下文属性
     * @param attrName 名称
     * @return attrValue 值
     */
    public Object getAttribute(String attrName);
    /**
     * 存储上下文属性
     * @param attrName 名称
     * @param attrValue 值
     */
    public void setAttribute(String attrName, Object attrValue);
    /**
     * 追加方式存储上下文变量
     * @param attrName 
     * @param attrValue
     */
    public void appendSortedAttr(String attrName, String attrValue);
}
