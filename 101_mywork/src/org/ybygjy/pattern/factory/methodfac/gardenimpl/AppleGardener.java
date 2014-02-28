package org.ybygjy.pattern.factory.methodfac.gardenimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;
import org.ybygjy.pattern.factory.methodfac.FruitGardener;
import org.ybygjy.pattern.factory.methodfac.fruitimpl.Apple;

/**
 * factory used to create AppleGardener instance
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class AppleGardener extends FruitGardener {

    @Override
    protected Fruit doCreateFruitFactory() {
        return new Apple();
    }
}
