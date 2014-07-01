package org.ybygjy.basic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字相关类的学习
 * @author WangYanCheng
 * @version 2014-7-1
 */
public class NumbericTest {
    public void testBigDecimalDivide() {
        BigDecimal bigDecimal = new BigDecimal(new Double(12341234));
        BigDecimal divideResult = bigDecimal.divide(new BigDecimal(100D), 2, RoundingMode.HALF_UP);
        System.out.println(divideResult.doubleValue());
    }
    public static void main(String[] args) {
        new NumbericTest().testBigDecimalDivide();
    }
}
