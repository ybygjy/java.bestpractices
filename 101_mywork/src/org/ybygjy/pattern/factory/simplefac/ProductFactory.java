package org.ybygjy.pattern.factory.simplefac;

import org.ybygjy.pattern.factory.simplefac.product.CarProduct;
import org.ybygjy.pattern.factory.simplefac.product.FruitProduct;

/**
 * product factory used to create the product instance
 * @author WangYanCheng
 * @version 2009-12-2
 */
public class ProductFactory {
    /**singleton instance*/
    private static ProductFactory productFac = new ProductFactory();
    /**
     * private constructor
     */
    private ProductFactory() {
    }
    /**
     * {@link ProductFactory} to get
     * @return productFac instance
     */
    public static final ProductFactory getInstance() {
        return productFac;
    }
    /**
     * get product on the productCode
     * @param productCode productLine code
     * @return productInst
     */
    public AbstractProduct getProduct(String productCode) {
        AbstractProduct productInst = null;
        if ("fruit".equalsIgnoreCase(productCode)) {
            productInst = new FruitProduct();
        } else if ("car".equalsIgnoreCase(productCode)) {
            productInst = new CarProduct();
        }
        return productInst;
    }
}
