package org.ybygjy.pattern.flyweight.simple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ybygjy.pattern.flyweight.simple.impl.ConcreteFlyweight;

/**
 * Flyweight factory
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class FlyweightFactory {
    /**globalCache*/
    private Map<Character, Object> globalCache = new HashMap<Character, Object>();
    /**flyweight factory*/
    private static FlyweightFactory flyweightFactInst = new FlyweightFactory();
    /**
     * private Constructor
     */
    private FlyweightFactory() {
    }
    /**
     * do get the flyweightfactory instance
     * @return fwFactInst instance
     */
    public static final FlyweightFactory getInstance() {
        return flyweightFactInst;
    }
    /**
     * get the flyweight instance
     * @param extrinsicState extrinsicState
     * @return flyweight instance
     */
    public AbstractFlyweight doGetFlyweight(Character extrinsicState) {
        if (globalCache.containsKey(extrinsicState)) {
            return (AbstractFlyweight) globalCache.get(extrinsicState);
        }
        AbstractFlyweight absFlyweightObj = new ConcreteFlyweight(extrinsicState);
        globalCache.put(extrinsicState, absFlyweightObj);
        return absFlyweightObj;
    }
    /**
     * do check the globalCache container
     */
    public void doCheckFlyweight() {
        for (Iterator iterator = globalCache.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entryObj = (Map.Entry) iterator.next();
            System.out.println(entryObj.getKey() + ":" + entryObj.getValue());
        }
    }
}
