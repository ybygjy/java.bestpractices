package org.ybygjy.pattern.factory.abstractfac;

/**
 * javaBean
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class CompexBean {
    /**
     * productA
     */
    private AbstractProductA productA;
    /**
     * productB
     */
    private AbstractProductB productB;
    /**
     * Constructor
     * @param productA productA
     * @param productB productB
     */
    public CompexBean(AbstractProductA productA, AbstractProductB productB) {
        super();
        this.productA = productA;
        this.productB = productB;
    }
    @Override
    public String toString() {
        return (productA.getDesc() + "\t" + productB.getDesc());
    }
}
