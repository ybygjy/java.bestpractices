package org.ybygjy.basic.collect.maptest;


/**
 * java.util.Dictionary测试<br>
 * <strong>验证某个子类在继承父类中某方法与实现接口声明的方法重合时的处理</strong>
 * @author WangYanCheng
 * @version 2010-8-16
 */
public class DictionaryTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        InnerClassATestPart icatpInst = new InnerClassATestPart();
        System.out.println(icatpInst.getInnerSize4Interface().size());
        System.out.println(icatpInst.size());
    }
}
/**
 * 实现类声明
 * @author WangYanCheng
 * @version 2010-8-16
 */
abstract class InnerClassA {
    /**
     * 声明子类实现接口
     * @return rtnSize rtnSize
     */
    public abstract int size();
}
/***
 * 接口声明
 * @author WangYanCheng
 * @version 2010-8-16
 */
interface InnerInterfaceA {
    /**
     * 声明实现接口
     * @return rtnSize rtnSize
     */
    String size();
    //String size();
}
/**
 * InnerClassATestPart
 * @author WangYanCheng
 * @version 2010-8-16
 */
class InnerClassATestPart extends InnerClassA {
    @Override
    public int size() {
        return 0;
    }
    /**
     * get innerSize
     * @return innerSizeInst
     */
    public InnerSizeClass getInnerSize4Interface() {
        return new InnerSizeClass();
    }
    /**
     * InnerSizeClass
     * @author WangYanCheng
     * @version 2010-8-16
     */
    protected class InnerSizeClass implements InnerInterfaceA {
        /**
         * {@inheritDoc}
         */
        public String size() {
            return "Hello World. This is a debug message,come from InnerSizeClass";
        }
    }
}
