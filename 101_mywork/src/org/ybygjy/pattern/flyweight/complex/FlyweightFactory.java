package org.ybygjy.pattern.flyweight.complex;

import java.util.HashMap;
import java.util.Map;

import org.ybygjy.pattern.flyweight.complex.impl.CompositeFlyweight;
import org.ybygjy.pattern.flyweight.complex.impl.ConcreteFlyweight;

/**
 * Flyweight工厂
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class FlyweightFactory {
    /**singleton pattern*/
    private static FlyweightFactory ffInst;
    /**flyweight collection*/
    private static Map<Character, AbstractFlyweight> flyweightCollect;
    /**
     * Singleton Pattern
     */
    private FlyweightFactory() {
        flyweightCollect = new HashMap<Character, AbstractFlyweight>();
    }
    /**
     * 获取工厂实例
     * @return ffInst {@link FlyweightFactory}
     */
    public static FlyweightFactory getInstance() {
        if (ffInst == null) {
            ffInst = new FlyweightFactory();
        }
        return ffInst;
    }
    /**
     * 获取flyweight实例
     * @param character {@link Character}
     * @return flyweightInst {@link AbstractFlyweight}
     */
    public AbstractFlyweight flyweight(Character character) {
        AbstractFlyweight rtnFlyweight = null;
        if (flyweightCollect.containsKey(character)) {
            rtnFlyweight = flyweightCollect.get(character);
        } else {
            rtnFlyweight = new ConcreteFlyweight(character);
            flyweightCollect.put(character, rtnFlyweight);
        }
        return rtnFlyweight;
    }
    /**
     * 获取flyweight实例
     * @param compositeState {@link Character}
     * @return flyweightInst {@link AbstractFlyweight}
     */
    public AbstractFlyweight flyweight(String compositeState) {
        char[] charArr = compositeState.toCharArray();
        CompositeFlyweight compositFlyweight = new CompositeFlyweight();
        char tmpChar = 0;
        for (int i = 0; i < charArr.length; i++) {
            tmpChar = charArr[i];
            compositFlyweight.add(tmpChar, this.flyweight(tmpChar));
        }
        return compositFlyweight;
    }
}
