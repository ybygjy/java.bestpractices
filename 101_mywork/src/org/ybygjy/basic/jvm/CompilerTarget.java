package org.ybygjy.basic.jvm;

import java.util.Date;

/**
 * 编译源文件
 * @author WangYanCheng
 * @version 2011-2-18
 */
public class CompilerTarget {
    public void doSometing( ){
        Date date = new Date(10, 3, 3);
        System.out.println(date.getTime());
    }
}
