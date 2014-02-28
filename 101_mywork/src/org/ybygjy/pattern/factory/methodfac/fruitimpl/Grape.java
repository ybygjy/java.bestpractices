package org.ybygjy.pattern.factory.methodfac.fruitimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;

/**
 * фоля
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class Grape extends Fruit {

    @Override
    protected void grow() {
        System.out.println("Grape growing.");
    }

    @Override
    protected void harvest() {
        System.out.println("Grape harvest.");
    }

    @Override
    protected void plant() {
        System.out.println("Grape plant.");
    }
}
