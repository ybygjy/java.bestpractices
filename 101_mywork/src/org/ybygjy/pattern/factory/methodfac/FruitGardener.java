package org.ybygjy.pattern.factory.methodfac;
/**
 * fruit gardener
 * @author WangYanCheng
 * @version 2009-12-2
 */
public abstract class FruitGardener {
    /**create fruit instance used to factory implements*/
    protected Fruit fruitObj = null;
    /**
     * doCreateFruitInstance
     * @return fruitInst fruitInst
     */
    protected abstract Fruit doCreateFruitFactory();
}
