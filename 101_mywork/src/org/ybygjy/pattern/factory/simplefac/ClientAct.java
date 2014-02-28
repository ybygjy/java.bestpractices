package org.ybygjy.pattern.factory.simplefac;
/**
 * user class
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class ClientAct {
    /**
     * debug portal
     * @param args arguments list
     */
    public static void main(String[] args) {
        ProductFactory productFactory = ProductFactory.getInstance();
        AbstractProduct appleProInst = productFactory.getProduct("fruit"),
        carProduct = productFactory.getProduct("car");
        appleProInst.desinging();
        appleProInst.manufacturing();
        appleProInst.sale();
        appleProInst.showInfo();
        carProduct.desinging();
        carProduct.manufacturing();
        carProduct.sale();
        carProduct.showInfo();
    }
}
