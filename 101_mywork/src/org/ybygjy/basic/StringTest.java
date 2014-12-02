package org.ybygjy.basic;

/**
 * <h3>字符串相关测试</h3>
 * @author WangYanCheng
 * @version 2011-2-28
 */
public class StringTest {
    /**
     * 测试给定字符串是否回文<strong>递归方式</strong>，如：<br>
     * <code>String str = "abcdcba</code>
     * @param str 源字符串
     * @return true/false
     */
    public boolean isLoopStr(String str) {
        char[] charStr = str.toCharArray();
        return innerIsLoopStr(charStr, 0, charStr.length - 1);
    }

    /**
     * 回文具体验证 <h4>确定边界</h4>
     * <p>
     * 1、字符组长度为1。
     * <p>
     * <p>
     * 2、字符组长度为2。
     * </p>
     * <p>
     * 3、字符组首尾相同。
     * </p>
     * @param charStr 源字符组
     * @param start 起始标记位
     * @param end 结束标记位
     * @return true/false
     */
    private boolean innerIsLoopStr(char[] charStr, int start, int end) {
        if (end == start || (end - start) == 1) {
            return true;
        }
        return charStr[start] == charStr[end] ? innerIsLoopStr(charStr, ++start, --end) : false;
    }
    /**
     * 不够3位自动补零
     */
    public static void testFillupZero() {
        int value =(int)(Math.random() * 1000);
        System.out.println("原值:".concat(String.valueOf(value)) + ",转换值" + String.format("-%03d", value));
    }
    
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
//        String str = "abcdcba";
//        System.out.println(new StringTest().isLoopStr(str));
        for (int i = 0; i < 100; i++) {
            StringTest.testFillupZero();
        }
    }
}
