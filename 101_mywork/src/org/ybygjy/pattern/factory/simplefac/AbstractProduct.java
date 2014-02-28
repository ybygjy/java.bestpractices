package org.ybygjy.pattern.factory.simplefac;

/**
 * 抽象产品
 * @author WangYanCheng
 * @version 2009-12-2
 */
public abstract class AbstractProduct {
    /**
     * 显示产品信息
     * @return productInfo
     */
    protected abstract String showInfo();
    /**
     * 设计阶段
     */
    protected abstract void desinging();
    /**
     * 生产阶段
     */
    protected abstract void manufacturing();
    /**
     * 销售
     */
    protected abstract void sale();
}
