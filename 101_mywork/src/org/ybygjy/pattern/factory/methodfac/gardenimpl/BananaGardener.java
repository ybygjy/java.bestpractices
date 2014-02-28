package org.ybygjy.pattern.factory.methodfac.gardenimpl;

import org.ybygjy.pattern.factory.methodfac.Fruit;
import org.ybygjy.pattern.factory.methodfac.FruitGardener;
import org.ybygjy.pattern.factory.methodfac.fruitimpl.Banana;

/**
 * factory used to create BananaFruit
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class BananaGardener extends FruitGardener {

    @Override
    protected Fruit doCreateFruitFactory() {
        return new Banana();
    }

}
