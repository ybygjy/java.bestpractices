package org.ybygjy.basic.classload;

/**
 * 类的静态变量的初始化
 * @author WangYanCheng
 * @version 2016年3月9日
 */
public class SingletonTest {
    public static void main(String[] args) {
        Singleton singInst = Singleton.getInst();
        System.out.println(singInst.getCount1());
        System.out.println(singInst.getCount2());
    }
}
class Singleton {
	private static final Singleton singleton = new Singleton();
    private static int count1;
    private static int count2 = 0;
    public Singleton() {
        this.count1++;
        this.count2++;
    }
    public static int getCount1() {
        return count1;
    }
    public static int getCount2() {
        return count2;
    }
    public static Singleton getInst() {
        return singleton;
    }
}
