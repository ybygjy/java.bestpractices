package org.ybygjy.basic.bit;

import java.util.Random;

/**
 * 与byte相关的测试
 * @author WangYanCheng
 * @version 2010-6-12
 */
public class ByteTest {
    /**
     * 普通字节打印
     */
    public void doPrintByte() {
        byte b1 = 127;
        p(b1);
    }
    /**
     * 移位运算
     * @param inParam inParam
     */
    public void doShiftTest(int[] inParam) {
        for (int tmpInt : inParam) {
            p("BEGIN " + tmpInt + ":" + Integer.toBinaryString(tmpInt));
            tmpInt = tmpInt >> 16;
            p("END " + tmpInt + ":" + Integer.toBinaryString(tmpInt));
        }
    }
    /**
     * 负责测试数组作用域
     * @param byteArr byteArr
     */
    public void doDomainTest(byte[] byteArr) {
        Random rand = new Random();
        for (int i = byteArr.length - 1; i >= 0; i--) {
            byteArr[i] = (byte) rand.nextInt();
        }
    }
    /**
     * 负责测试数组作用域入口
     */
    public void doDomainTestMain() {
        byte[] byteArr = new byte[16];
        doDomainTest(byteArr);
        System.out.println(new String(byteArr));
    }
    /**
     * 测试二进制四则运算
     */
    public void doTest() {
        int flag = 0xF;
        p(Integer.toBinaryString(flag));
        //右第2位变成1111-->1011
        flag = flag & ~(1 << 2);
        p("右第3位变成0:" + Integer.toBinaryString(flag));
        flag = flag ^ (1 << 2);
        p("右第3位取反   :" + Integer.toBinaryString(flag));
        flag = flag & ((1 << 3) - 1);
        p("取未3位        :" + Integer.toBinaryString(flag));
        flag = flag & ((1 << 2) - 1);
        p("取未k位k=2:" + Integer.toBinaryString(flag));
        flag = (flag >> (2 - 1)) & 1;
        p("取右数k位k=2:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag | ((1 << 3) - 1);
        p("右k位变为1,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag & (~((1 << 3) - 1));
        p("右k位变为0,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag ^ ((1 << 3) - 1);
        p("右k位取反,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag & (flag + 1);
        p("连续的1置为0,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag | (flag + 1);
        p("第一个0置为1,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag | (flag - 1);
        p("连续的0置为1,k=3:" + Integer.toBinaryString(flag));
        flag = (flag ^ (flag + 1)) >> 1;
        p("取连续的1,k=3:" + Integer.toBinaryString(flag));
        flag = 0xF;
        flag = flag & (flag ^ (flag - 1));
        p("取第一个1右边,k=3:" + Integer.toBinaryString(flag));
    }
    /**
     * 测试工具,打印
     * @param obj obj
     */
    public static void p(Object obj) {
        System.out.println(obj);
    }
    public void doPrintStr4Binary(String str) {
        StringBuffer sbuf = new StringBuffer();
        byte[] byteArr = str.getBytes();
        for (int i : byteArr) {
            sbuf.append(Integer.toBinaryString(i)).append(" ");
        }
        System.out.println(sbuf.toString());
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ByteTest btInst = new ByteTest();
//        btInst.doShiftTest(new int[]{1024, 65536, 65535});
//        btInst.doPrintByte();
//        btInst.doDomainTestMain();
//        btInst.doTest();
        btInst.doPrintStr4Binary("HelloWorld");
    }
}
