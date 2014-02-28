package org.ybygjy.pattern.factory.methodfac.fruitimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;

/**
 * apple
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class Apple extends Fruit {

    @Override
    protected void grow() {
        System.out.println("Apple growing.");
    }

    @Override
    protected void harvest() {
        System.out.println("Harvest apples.");
    }

    @Override
    protected void plant() {
        System.out.println("Plant apple.");
    }
}
