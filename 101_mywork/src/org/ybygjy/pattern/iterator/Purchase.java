package org.ybygjy.pattern.iterator;

import java.util.Vector;

/**
 * 抽象的购物框
 * @author WangYanCheng
 * @version 2012-11-26
 */
public abstract class Purchase {
    protected Vector elements = new Vector(5);
    public abstract Iterator createIterator();
    public void append(Object obj) {
        elements.add(obj);
    }
    public void remove(Object obj) {
        elements.remove(obj);
    }
    public Object currentItem(int index) {
        return elements.get(index);
    }
    public int count() {
        return elements.size();
    }
}
