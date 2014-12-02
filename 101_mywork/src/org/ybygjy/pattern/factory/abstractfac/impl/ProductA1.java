package org.ybygjy.pattern.factory.abstractfac.impl;

import org.ybygjy.pattern.factory.abstractfac.AbstractProductA;
/**
 * 抽象产品实现
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class ProductA1 implements AbstractProductA {
    /**
     * {@inheritDoc}
     */
    public String getDesc() {
        return this.toString();
    }
}
