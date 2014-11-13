package org.ybygjy.basic.bit;

import java.util.Random;

/**
 * 随机数学习_补齐
 * @author WangYanCheng
 * @version 2014-6-30
 */
public class RandomNumberTest {
    /**
     * 通过模除取给定区间的随机数
     * @param min
     * @param max
     * @return
     */
    public int nextIntF(int min, int max) {
        Random random = new Random();
        int tmp = Math.abs(random.nextInt());
        tmp = tmp % (max - min + 1) + min;
        return tmp;
    }
    /**
     * 通过随机结果补位
     * @return
     */
    public int nextIntS() {
        Random random = new Random();
        int x = random.nextInt(899999);
        return x + 100000;
    }
    /**
     * 通过随机数乘法运算间接实现补位
     * @return
     */
    public int nextIntT() {
        int n = 0;
        while (n < 100) {
            n = (int) (Math.random() * 1000000);
        }
        return n;
    }
    /**
     * 小数放大补整继续放大取整
     * @return rtnC
     */
    public int nextIntFor() {
    	double tmpV = Math.random();
    	tmpV = tmpV * 9;
    	System.out.println(tmpV + '\t');
    	tmpV = tmpV + 1;
    	System.out.println(tmpV + '\t');
    	tmpV = tmpV * 1000000;
    	System.out.println(tmpV + '\t');
    	int rtnV = (int) tmpV;
    	System.out.println(rtnV + '\t');
        return rtnV;
    }
    /**
     * 取给定位数随机数(限制2~8位)
     * @param radix
     * @return rtnValue
     */
    public int getRandomValue(int radix) {
        return ((int) ((Math.random() + 1) * (radix < 2 ? 100 : radix > 9 ? 100 : Math.pow(10D, radix))));
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        RandomNumberTest rntInst = new RandomNumberTest();
        for (int i = 0; i < 100; i++) {
//            System.out.println(rntInst.nextIntF(100, 200));
//            System.out.println(rntInst.nextIntS());
//            System.out.println(rntInst.nextIntT());
            System.out.println(rntInst.nextIntFor());
//            System.out.println(rntInst.nextIntFor());
//            Long batchNo = Long.parseLong(String.valueOf(rntInst.getRandomValue(8)) + "" + String.valueOf(rntInst.getRandomValue(8)));
//            System.out.println(batchNo);
        }
    }
}
