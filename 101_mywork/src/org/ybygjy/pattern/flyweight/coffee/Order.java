package org.ybygjy.pattern.flyweight.coffee;
/**
 * 订单
 * @author WangYanCheng
 * @version 2010-11-22
 */
public abstract class Order {
    /**
     * 将coffee卖给客人
     * @param table {@link Table}
     */
    public abstract void serve(Table table);
    /**
     * 取coffee名称
     * @return coffeeName
     */
    public abstract String getFlavor();
}
