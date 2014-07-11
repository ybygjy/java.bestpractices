package org.ybygjy.basic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 数字相关类的学习
 * @author WangYanCheng
 * @version 2014-7-1
 */
public class NumbericTest {
    public void testBigDecimalDivide() {
        BigDecimal bigDecimal = new BigDecimal(12545);
        BigDecimal divideResult = bigDecimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        System.out.println(divideResult.doubleValue());
        System.out.println("divideResult.toEngineeringString()=>" + divideResult.toEngineeringString());
        System.out.println("divideResult.toPlainString()=>" + divideResult.toPlainString());
        System.out.println("divideResult.toString()=>" + divideResult.toString());
    }
    public void testRound() {
        double[] dataArgs = new double[]{3.1D, 3.2D, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0};
        for (int i = 0; i < dataArgs.length; i++) {
            BigDecimal tmpIns = new BigDecimal(dataArgs[i], MathContext.DECIMAL32).setScale(0, RoundingMode.UP);
            System.out.println(tmpIns.plus());
        }
    }
    public static void main(String[] args) {
        new NumbericTest().testBigDecimalDivide();
    }
}
