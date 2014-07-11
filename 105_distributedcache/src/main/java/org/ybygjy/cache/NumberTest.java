package org.ybygjy.cache;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NumberTest {
	public static void main(String [] args) {
		double[] dataArgs = new double[]{3.1D, 3.2D, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0};
		for (int i = 0; i < dataArgs.length; i++) {
			BigDecimal tmpIns = new BigDecimal(dataArgs[i], MathContext.DECIMAL32).setScale(0, RoundingMode.UP);
			System.out.println(tmpIns.plus());
		}
	}
}
