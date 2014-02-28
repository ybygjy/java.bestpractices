package org.ybygjy.pattern.factory.abstractfac.impl;

import org.ybygjy.pattern.factory.abstractfac.AbstractFactory;
import org.ybygjy.pattern.factory.abstractfac.CompexBean;

/**
 * 工厂规范实现
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class ConcreteFactoryA implements AbstractFactory {
    /**
     * {@inheritDoc}
     */
    public CompexBean doCreateProductA() {
        return new CompexBean(new ProductA1(), new ProductB1());
    }

    /**
     * {@inheritDoc}
     */
    public CompexBean doCreateProductB() {
        return new CompexBean(new ProductA2(), new ProductB2());
    }

}
