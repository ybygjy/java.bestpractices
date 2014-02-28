package org.ybygjy.pattern.flyweight.coffee;

import org.ybygjy.pattern.flyweight.coffee.impl.Flavor;

/**
 * 享元工厂
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class FlavorFactory {
    /** singleton pattern */
    private static FlavorFactory ffInst = null;
    /** orders */
    private Order[] orders = new Order[10];
    /** orders made */
    private int ordersMade = 0;
    /** total flavors */
    private int totalFlavors = 0;

    /**
     * Constructor
     */
    private FlavorFactory() {
    }

    /**
     * 提供享元对象实例
     * @param flavorToGet flavorToGet
     * @return orderInst {@link Order}
     */
    public Order getOrder(String flavorToGet) {
        if (ordersMade > 0) {
            for (int i = 0; i < ordersMade; i++) {
                if (flavorToGet.equals(orders[i].getFlavor())) {
                    return orders[i];
                }
            }
        }
        orders[ordersMade] = new Flavor(flavorToGet);
        totalFlavors++;
        return orders[ordersMade++];
    }

    /**
     * @return the totalFlavors
     */
    public int getTotalFlavors() {
        return totalFlavors;
    }

    /**
     * 取工厂实例
     * @return flavorFactory {@link FlavorFactory}
     */
    public static FlavorFactory getInstance() {
        if (ffInst == null) {
            ffInst = new FlavorFactory();
        }
        return ffInst;
    }
}
