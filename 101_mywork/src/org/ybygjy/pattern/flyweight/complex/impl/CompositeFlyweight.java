package org.ybygjy.pattern.flyweight.complex.impl;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.ybygjy.pattern.flyweight.complex.AbstractFlyweight;

/**
 * 组合单纯Flyweight策略
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class CompositeFlyweight extends AbstractFlyweight {
    /** FlyweightCollection */
    private Map<Character, AbstractFlyweight> flyweightCollect = new Hashtable<Character, AbstractFlyweight>();

    /**
     * add
     * @param key key
     * @param flyweight {@link AbstractFlyweight}
     */
    public void add(Character key, AbstractFlyweight flyweight) {
        flyweightCollect.put(key, flyweight);
    }

    @Override
    public void doSomething(String extrinsicState) {
        for (Iterator<Map.Entry<Character, AbstractFlyweight>> iterator = flyweightCollect.entrySet()
            .iterator(); iterator.hasNext();) {
            Map.Entry<Character, AbstractFlyweight> entity = iterator.next();
            entity.getValue().doSomething(extrinsicState);
        }
    }
}
