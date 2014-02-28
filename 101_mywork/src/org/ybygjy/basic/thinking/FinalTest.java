package org.ybygjy.basic.thinking;

import java.util.Random;

/**
 * 测试<i>final</i>修饰符
 * @author WangYanCheng
 * @version 2010-5-29
 * <br>
 * <i>结论:</i>
 * <li>final可以保障基本类型不被修改[只读]</li>
 * <li>final可以保障引用类型内存地址不被修改</li>
 * <li>final不能保障引用类型自身内容的修改(从测试过程，数组测试可以看到)</li>
 */
public class FinalTest {
    /**randInst*/
    private static java.util.Random randInst = new Random();
    /**finalData*/
    private String id;
    /**
     * constructor
     * @param id id
     */
    public FinalTest(String id) {
        this.id = id;
    }
    /**compile time constants*/
    private final int VAL_ONE = 9;
    /**compile time constants*/
    private static final int VAL_TWO = 99;
    /**compile time constants*/
    public static final int VAL_THREE = 39;
    private final int i4 = randInst.nextInt(20);
    static final int i5 = randInst.nextInt(20);
    private Value v1 = new Value(11);
    private final Value v2 = new Value(12);
    private static final Value v3 = new Value(13);
    private final int[] a = {1, 2, 3, 4, 5, 6};
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return id + ": i4= " + i4 + ", i5=" + i5;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        FinalTest ftInst = new FinalTest("H");
        p("Instance print==>" + ftInst);
        /*测试数组 BEGIN*/
        p("Array a toString==> " + ftInst.a + "::" + ftInst.a[0]);
        ftInst.a[0] = 100;
        p("Array a toString==> " + ftInst.a + "::" + ftInst.a[0]);
        /*测试数组 END*/
        /*测试对象实例 BEGIN*/
        p("v2=BEGIN=>" + ftInst.v2.toString());
        ftInst.v2.refInst = new Object();
        for (int i = 0; i < 10; i++) {
            ftInst.v2.i++;
        }
        p("v2=END=>" + ftInst.v2.toString());
        /*测试对象实例 END*/
    }
    /**
     * 输出对像实例
     * @param obj obj
     */
    public static void p(Object obj) {
        System.out.println(obj.toString());
    }
}
/**
 * 值对象
 * @author WangYanCheng
 * @version 2010-5-29
 */
class Value {
    int i;
    public Object refInst = null;
    /**
     * constructor
     * @param i i
     */
    public Value(int i) {
        this.i = i;
        this.refInst = new Object();
    }
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "value::" + this.i + "::" + refInst;
    }
}
