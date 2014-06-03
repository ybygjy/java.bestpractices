package org.ybygjy.basic.charset;


/**
 * Charset学习
 * @author WangYanCheng
 * @version 2014-5-20
 */
public class CharsetTest {
    /**
     * 二进制格式打印ASCII字符集内容
     */
    public void doPrintAscii() {
        int count = 1;
        for (int i = 0x00; i <= 0x7F; i++) {
            System.out.print(doFillInStr(Integer.toBinaryString(i)));
            System.out.print(' ');
            if (count ++ % 32 == 0) {
                System.out.println();
            }
        }
    }
    /**
     * 补零,给定字符串验证字符数,不够8个字符左边补零,不处理字符串字符数超8个的情况
     * @param str 源字符串
     * @return str 补零后的字符串
     */
    public static String doFillInStr(String str) {
        int len = str.length();
        if (len >= 8) {
            return str;
        }
        int spaceCount = Math.abs(8 - len);
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < spaceCount; i++) {
            sbuf.append("0");
        }
        return sbuf.toString().concat(str);
    }
    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args) {
        CharsetTest charsetTest = new CharsetTest();
        charsetTest.doPrintAscii();
    }
}
