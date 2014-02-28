package org.ybygjy.pattern.factory.abstractfac;

import org.ybygjy.pattern.factory.abstractfac.impl.ConcreteFactoryA;
import org.ybygjy.pattern.factory.abstractfac.impl.ConcreteFactoryB;

/**
 * 抽象工厂模式测试入口
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class FactoryTestDrive {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        AbstractFactory af = new ConcreteFactoryA();
        af.doCreateProductA().toString();
        af.doCreateProductB().toString();
        af = new ConcreteFactoryB();
        af.doCreateProductA().toString();
        af.doCreateProductB().toString();
    }
}
