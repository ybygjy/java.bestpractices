package org.ybygjy.pattern.factory.simplefac.product;

import org.ybygjy.pattern.factory.simplefac.AbstractProduct;
/**
 * product car
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class CarProduct extends AbstractProduct {

    @Override
    protected void desinging() {
        System.out.println("designing carProduct.");
    }

    @Override
    protected void manufacturing() {
        System.out.println("manufacturing car..");
    }

    @Override
    protected void sale() {
        System.out.println("sales carProduct.");
    }

    @Override
    protected String showInfo() {
        return "[carProduct]";
    }

}
