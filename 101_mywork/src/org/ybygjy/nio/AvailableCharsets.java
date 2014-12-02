package org.ybygjy.nio;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * 负责打印当前系统可用字符集
 * @author WangYanCheng
 * @version 2010-11-10
 */
public class AvailableCharsets {
    /**
     * 测试入口
     * @param args arguments
     */
    public static void main(String[] args) {
        Map charSets = Charset.availableCharsets();
        Iterator iterator = charSets.keySet().iterator();
        while (iterator.hasNext()) {
            String csName = (String) iterator.next();
System.out.print(csName);
            Iterator innerIter = ((Charset) charSets.get(csName)).aliases().iterator();
            if (innerIter.hasNext()) {
                System.out.print(": ");
            }
            while (innerIter.hasNext()) {
                System.out.print(innerIter.next());
                if (innerIter.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
