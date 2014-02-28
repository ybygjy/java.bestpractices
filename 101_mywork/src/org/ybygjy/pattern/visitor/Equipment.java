package org.ybygjy.pattern.visitor;

/**
 * 定义被访问者
 * <p>当前场景是计算机设备的抽象
 * @author WangYanCheng
 * @version 2013-2-6
 */
public interface Equipment {
    public void accept(Visitor visitor);
    public double price();
}
