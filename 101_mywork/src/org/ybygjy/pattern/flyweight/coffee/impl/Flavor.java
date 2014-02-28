package org.ybygjy.pattern.flyweight.coffee.impl;

import org.ybygjy.pattern.flyweight.coffee.Order;
import org.ybygjy.pattern.flyweight.coffee.Table;

/**
 * 单纯享元对象
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class Flavor extends Order {
    /**flavor*/
    private String flavor;
    /**
     * Constructor
     * @param flavor flavor
     */
    public Flavor(String flavor) {
        this.flavor = flavor;
    }

    @Override
    public String getFlavor() {
        return this.flavor;
    }

    @Override
    public void serve(Table table) {
        System.out.println("Serving table:" + table.getNumber() + "\t with flavor:" + flavor);
    }
}
