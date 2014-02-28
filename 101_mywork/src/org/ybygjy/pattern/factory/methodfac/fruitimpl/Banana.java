package org.ybygjy.pattern.factory.methodfac.fruitimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;
/**
 * banana
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class Banana extends Fruit {

    @Override
    protected void grow() {
        System.out.println("Banana growing.");
    }

    @Override
    protected void harvest() {
        System.out.println("Banana harvest.");
    }

    @Override
    protected void plant() {
        System.out.println("Banana plant.");
    }

}
