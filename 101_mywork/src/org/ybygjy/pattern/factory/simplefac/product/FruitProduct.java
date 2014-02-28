package org.ybygjy.pattern.factory.simplefac.product;

import org.ybygjy.pattern.factory.simplefac.AbstractProduct;
/**
 * product implements
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class FruitProduct extends AbstractProduct {

    @Override
    protected void desinging() {
        System.out.println("Fruits are growning");
    }

    @Override
    protected void manufacturing() {
        System.out.println("Fruits can harvest.");
    }

    @Override
    protected void sale() {
        System.out.println("Fruits are holt sale.");
    }

    @Override
    protected String showInfo() {
        return "{Fruit}";
    }
}
