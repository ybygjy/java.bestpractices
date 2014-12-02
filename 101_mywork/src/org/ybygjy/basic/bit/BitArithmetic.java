package org.ybygjy.basic.bit;
/**
 * 位运算_基础
 * @author WangYanCheng
 * @version 2010-3-31
 */
public class BitArithmetic {
    /**
     * 使用异或完成不需要监听变量,两变量值的交换
     * @param x x
     * @param y y
     */
    public void doSwap(int x, int y) {
        System.out.println("before swap==>" + x + ":" + y);
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("after swap==>" + x + ":" + y);
    }
    /**
     * 取反运算
     * @param x x
     */
    public void notArith(int x) {
        short y = (short) x;
        byte[] byteInst = new byte[32];
        byteInst = Short.toString(y).getBytes();
        for (int index = 0; index < byteInst.length; index++) {
            System.out.println(Integer.toBinaryString(byteInst[index]));
        }
    }
    /**
     * 测试入口
     * @param args arguments
     */
    public static void main(String[] args) {
        BitArithmetic brInst = new BitArithmetic();
        brInst.doSwap(10, 20);
        brInst.notArith(1);
    }
}
