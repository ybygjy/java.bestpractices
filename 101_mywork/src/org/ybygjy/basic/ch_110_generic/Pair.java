package org.ybygjy.basic.ch_110_generic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 定义泛型类
 * @author WangYanCheng
 * @version 2014-6-13
 */
public class Pair <T> implements Serializable {
    private T first;
    private T second;
    public Pair() {
    }
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() {
        return first;
    }
    public T getSecond() {
        return second;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public void setSecond(T second) {
        this.second = second;
    }
    /**
     * 泛型方法
     * @param <T> 类型变量
     * @param t 参数
     * @return rtnValue T
     */
    public <T1 extends T> T1 getMiddle(T1[] t) {
        return t[t.length / 2];
    }
    /**
     * 泛型类型变量的限定
     * @param <T> 类型变量，这里限定类型必须实现了{@link Comparable}接口
     * @param a 参数
     * @return rtnT
     */
    public <T extends Comparable<T>> T min(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T smallest = a[0];
        for (int i = 1; i < a.length; i++) {
            if (smallest.compareTo(a[i]) > 0) {
                smallest = a[i];
            }
        }
        return smallest;
    }
    /**
     * 泛型方法#寻找数组最大、最小值
     * @param <T> 类型变量，必须实现{@link Comparable}接口
     * @param a 参数
     * @return rtnT
     */
    public static <T1 extends Comparable> Pair<T1> minMax(T1[] a) {
        if (null == a) {
            return null;
        }
        T1 min = a[0];
        T1 max = min;
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }
            if (max.compareTo(a[i]) < 0) {
                max = a[i];
            }
        }
        return new Pair<T1>(max, min);
    }
    /**
     * 在异常声明中使用类型变量
     * @param <T> 类型变量
     * @throws T 限定了参数必须为{@link Throwable}类型子类
     */
    public static <T extends Throwable> void throwException(T t) throws T {
        int b = 10;
        try {
            b = b/0;
        } catch (Exception e) {
            t.initCause(e.fillInStackTrace());
            throw t;
        }
    }
    /**
     * 泛型数组
     */
    public static void genericTypeArray() {
        Pair<String>[] t = new Pair[10];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Pair<String>("" + i, "" + i);
        }
        for (Pair<String> tItem : t) {
            System.out.println(tItem);
        }
    }
    @Override
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        testDoWork1();
        testDoWork2();
        testThrowException();
        testGenericArray();
    }
    /**
     * 测试1
     */
    public static void testDoWork1() {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        String min = words[0];
        String max = min;
        for (int i = 0; i < words.length; i++) {
            if (min.compareTo(words[i]) > 0) {
                min = words[i];
            }
            if (max.compareTo(words[i]) < 0) {
                max = words[i];
            }
        }
        Pair<String> minMax = new Pair<String>(min, max);
        String middleName = minMax.<String>getMiddle(words);
        System.out.println("Max:" + minMax.getFirst());
        System.out.println("Min:" + minMax.getSecond());
        System.out.println("MiddleName:" + middleName);
    }
    /**
     * 测试2
     */
    public static void testDoWork2() {
        GregorianCalendar[] gregorianCalendar = {
            new GregorianCalendar(2008, Calendar.MARCH, 20),
            new GregorianCalendar(2011, Calendar.APRIL, 11),
            new GregorianCalendar(2013, Calendar.MAY, 23)
        };
        Pair<GregorianCalendar> minMaxPair = minMax(gregorianCalendar);
        System.out.println("Max:" + minMaxPair.getFirst());
        System.out.println("Min:" + minMaxPair.getSecond());
    }
    /**
     * 测试3
     * 在异常声明中使用了泛型变量
     */
    public static void testThrowException() {
        try {
            Pair.throwException(new RuntimeException());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试4
     * 泛型数组
     * 不能实例化参数化类型的数组
     */
    public static void testGenericArray() {
        Pair.genericTypeArray();
    }
}
