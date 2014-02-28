package org.ybygjy.pattern.flyweight.simple.impl;

import org.ybygjy.pattern.flyweight.simple.AbstractFlyweight;

/**
 * Concrete flyweight
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class ConcreteFlyweight extends AbstractFlyweight {
    /**Intrinsic state*/
    private Character charObj = null;
    /**
     * Constructor
     * @param charObjE extrinsic
     */
    public ConcreteFlyweight(Character charObjE) {
        this.charObj = charObjE;
    }
    @Override
    public void operation(String state) {
        System.out.println("Intrinsic state" + charObj + ";Extrinsic state" + state);
    }
}
