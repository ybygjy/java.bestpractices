package org.ybygjy.pattern.factory.abstractfac.impl;

import org.ybygjy.pattern.factory.abstractfac.AbstractProductB;

/**
 * 抽象产品规范
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class ProductB1 implements AbstractProductB {
    /**
     * {@inheritDoc}
     */
    public String getDesc() {
        return this.toString();
    }

}
