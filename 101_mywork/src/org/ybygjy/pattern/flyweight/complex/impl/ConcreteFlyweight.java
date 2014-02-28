package org.ybygjy.pattern.flyweight.complex.impl;

import org.ybygjy.pattern.flyweight.complex.AbstractFlyweight;

/**
 * 具体享元对象
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class ConcreteFlyweight extends AbstractFlyweight {
    /**intrinsicState*/
    private Character intrinsicState;
    /**
     * Constructor
     * @param intrinsicState intrinsticState
     */
    public ConcreteFlyweight(Character intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    @Override
    public void doSomething(String extrinsicState) {
        System.out.println("Intrinsic State:" + this.intrinsicState + "\nExtrinsic State" + extrinsicState);
    }
}
