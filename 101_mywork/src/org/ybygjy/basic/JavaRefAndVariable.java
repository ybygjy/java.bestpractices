package org.ybygjy.basic;

/**
 * <h3>Java传值传引用</h3>
 * <p>
 * 1、简单类型按值传递(pass by value)不可改变值
 * </p>
 * <p>
 * 2、引用类型传递可改变值
 * </p>
 * <p>
 * 3、String是特殊的不可变对象，传的是值不可改变值
 * </p>
 * <p>
 * 4、数组传递引用，可改变数组内部值，但不可改变数组自身。
 * </p>
 * @author WangYanCheng
 * @version 2011-2-28
 */
public class JavaRefAndVariable {
    /**
     * 字符串引用测试
     * @param srcStr 源字符串
     */
    public static void simpleType(String srcStr) {
        srcStr = srcStr.concat(srcStr).concat(srcStr);
        System.out.println("方法内部改变变量值==>" + srcStr);
    }

    /**
     * 基本变量引用
     * @param i i
     */
    public static void simpleType(int i) {
        i = i++;
        System.out.println("改变基本变量类值==>" + i);
    }

    /**
     * 传递引用类型
     * @param srcStr 源字符串
     */
    public static void objectType(StringBuffer srcStr) {
        srcStr.append("传引用，可改变值");
    }

    /**
     * 传递数组引用
     * <p>
     * 1、可改变数组值
     * </p>
     * <p>
     * 2、不可改变数组本身引用
     * </p>
     * @param a 数据
     */
    public static void arraySimpleType(int[] a) {
        a[2] = 10;
        a = new int[10];
    }

    /**
     * 测试入口
     * @param arg 参数列表
     */
    public static void main(String[] arg) {
        String srcStr = "ABCDEF";
        int i = 10;
        JavaRefAndVariable.simpleType(srcStr);
        JavaRefAndVariable.simpleType(i);
        StringBuffer sbufStr = new StringBuffer("Hello");
        int[] array = {1, 2, 3, 4};
        JavaRefAndVariable.objectType(sbufStr);
        JavaRefAndVariable.arraySimpleType(array);
    }
}
