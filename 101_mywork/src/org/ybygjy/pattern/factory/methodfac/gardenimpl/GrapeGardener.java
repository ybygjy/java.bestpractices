package org.ybygjy.pattern.factory.methodfac.gardenimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;
import org.ybygjy.pattern.factory.methodfac.FruitGardener;
import org.ybygjy.pattern.factory.methodfac.fruitimpl.Grape;

/**
 * factory used to create Grape fruit
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class GrapeGardener extends FruitGardener {

    @Override
    protected Fruit doCreateFruitFactory() {
        return new Grape();
    }
}
