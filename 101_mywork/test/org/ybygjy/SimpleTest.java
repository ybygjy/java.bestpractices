package org.ybygjy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * SimpleTest
 * @author WangYanCheng
 * @version 2010-12-8
 */
//@RunWith(TestRunner.class)
public class SimpleTest {
    @Test(expected=ArithmeticException.class)
    public void doTest() {
        int i = 0, j = 1;
        i = j / i;
    }
    public static String wordFormat(String name) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(name);
        StringBuffer rtnBuf = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(rtnBuf, matcher.group());
        }
        matcher.appendTail(rtnBuf);
        return rtnBuf.toString().toUpperCase();
    }
}
