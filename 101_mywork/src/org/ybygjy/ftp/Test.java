package org.ybygjy.ftp;
/**
 * 
 * @author WangYanCheng
 * @version 2011-5-31
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        String tmpStr = (args.length != 0 && args[0] != null) ? args[0] : "C:\\abc\\aaa\\";
        System.out.println(tmpStr.substring(tmpStr.lastIndexOf('\\') + 1));
        System.out.println(tmpStr.matches(".*[^\\\\]+$"));
    }
}
