package org.ybygjy.pattern.factory.abstractfac;

/**
 * 抽象工厂规范
 * @author WangYanCheng
 * @version 2010-10-25
 */
public interface AbstractFactory {
    /**
     * doCreateProductA
     * @return {@link AbstractProductA}
     */
    CompexBean doCreateProductA();

    /**
     * doCreateProductB
     * @return {@link AbstractProductB}
     */
    CompexBean doCreateProductB();
}
