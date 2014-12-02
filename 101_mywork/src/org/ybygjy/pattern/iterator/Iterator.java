package org.ybygjy.pattern.iterator;

/**
 * 定义迭代特为接口
 * @author WangYanCheng
 * @version 2012-11-26
 */
public interface Iterator {
    public void first();
    public void next();
    public boolean isDone();
    public Object currentItem();
}
