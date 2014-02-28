package org.ybygjy.pattern.factory.methodfac;

import org.ybygjy.pattern.factory.methodfac.gardenimpl.AppleGardener;
import org.ybygjy.pattern.factory.methodfac.gardenimpl.BananaGardener;
import org.ybygjy.pattern.factory.methodfac.gardenimpl.GrapeGardener;

/**
 * Farms
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class FarmsMgr {
    /**
     * debug portal
     * @param args 参数列表
     */
    public static void main(String[] args) {
        FruitGardener fruitGarden = new AppleGardener();
        Fruit fruitObj = fruitGarden.doCreateFruitFactory();
        doShowInfo(fruitObj);
        fruitGarden = new BananaGardener();
        doShowInfo(fruitGarden.doCreateFruitFactory());
        fruitGarden = new GrapeGardener();
        doShowInfo(fruitGarden.doCreateFruitFactory());
    }
    /**
     * show Fruit info
     * @param fruitObj fruitObj
     */
    public static void doShowInfo(Fruit fruitObj) {
        fruitObj.plant();
        fruitObj.grow();
        fruitObj.harvest();
    }
}
