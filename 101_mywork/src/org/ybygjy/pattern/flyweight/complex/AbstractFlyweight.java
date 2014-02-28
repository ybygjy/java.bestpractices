package org.ybygjy.pattern.flyweight.complex;
/**
 * 负责定义享元对象统一规范
 * @author WangYanCheng
 * @version 2010-11-22
 */
public abstract class AbstractFlyweight {
    /**
     * 相应操作
     * @param extrinsicState 外蕴对象状态
     */
    public abstract void doSomething(String extrinsicState);
}
