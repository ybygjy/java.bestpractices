package org.ybygjy.jcip.chap2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 计算因子
 * @author WangYanCheng
 * @version 2014年12月16日
 */
public class FactorClass {
	private BigInteger bigIntObj;
	public FactorClass(BigInteger bigIntObj) {
		this.bigIntObj = bigIntObj;
	}
	public BigInteger[] doWork() {
		List<BigInteger> tmpArr = new ArrayList<BigInteger>();
		//开方
		double doubInst = Math.sqrt(bigIntObj.doubleValue());
		//取整
		long longValue = (long) doubInst + 1;
		for (int i = 1; i < longValue; i++) {
			//整除取因子
			BigInteger[] resultArr = bigIntObj.divideAndRemainder(BigInteger.valueOf(i));
			if (resultArr[1].intValue() == 0) {
				tmpArr.add(resultArr[0]);
				tmpArr.add(BigInteger.valueOf(i));
			}
		}
		return tmpArr.toArray(new BigInteger[tmpArr.size()]);
	}
	public static void main(String[] args) {
		FactorClass factorClass = new FactorClass(BigInteger.valueOf(999));
		BigInteger[] tmpArr = factorClass.doWork();
		System.out.println(Arrays.toString(tmpArr));
	}
}
