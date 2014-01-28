package org.ybygjy;

/**
 * 负责定义侦听器结构
 * @author WangYanCheng
 * @version 2012-4-26
 */
public interface MessageListener {
    /**
     * 事务前期切面
     */
    public void beforeListener();

    /**
     * 事务后期切面
     */
    public void afterListener();
}
