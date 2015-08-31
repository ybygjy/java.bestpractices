package org.ybygjy.util;

/**
 * Base64编码
 * <p>1、将字符串转换成字节数组</p>
 * <p>2、每3个字节为一组</p>
 * <p>3、每组进行位运算由3组构造出4组</p>
 * <p>4、4组中的字节转成10进制</p>
 * <p>5、去Base64编码表查对应的字符</p>
 * @author WangYanCheng
 * @version 2013-5-6
 */
public class Base64 {
    private static String baseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static String encode(String plainText) {
        String encoded = "";
        int paddingCount = plainText.length() % 3;
        String paddingStr = "";
        if (paddingCount > 0) {
            for (; paddingCount < 3; paddingCount++) {
                paddingStr += "=";
                plainText += "\0";
            }
        }
        for (paddingCount = 0; paddingCount < plainText.length(); paddingCount += 3) {
            if (paddingCount > 0 && (paddingCount / 3 * 4) % 76 == 0) {
                encoded += "\r\n";
            }
            int n = (plainText.charAt(paddingCount) << 16) + (plainText.charAt(paddingCount + 1) << 8) + (plainText.charAt(paddingCount + 2));
            int n1 = (n >> 18) & 63, n2 = (n >> 12) & 63, n3 = (n >> 6) & 63, n4 = n & 63;
            encoded += "" + baseStr.charAt(n1) + baseStr.charAt(n2) + baseStr.charAt(n3) + baseStr.charAt(n4);
        }
        return encoded.substring(0, encoded.length() - paddingStr.length()) + paddingStr;
    }
    public static void main(String[] args) {
        String str = Base64.encode("aa824cdd7d977aa43e5bb91fedb13fc9844585fd");
        System.out.println(str);
    }
}
