package org.ybygjy.basic.reflection.test2;

import java.lang.reflect.Method;

/**
 * CSDN网友关于反射问题
 * @author WangYanCheng
 * @version 2010-8-5
 */
public class ReflectionTest4CSDN {
    /**
     * 取得子类非继承Method
     * @param classInst 对象实例
     */
    private void refChildClassMethod(Class classInst) {
        Method[] methodArr = classInst.getDeclaredMethods();
        for (Method tmpM : methodArr) {
            System.out.println(tmpM.getName());
        }
    }
    /**
     * 测试入口
     * @param arg 参数列表
     */
    public static void main(String[] arg) {
        ReflectionTest4CSDN rt4cInst = new ReflectionTest4CSDN();
        rt4cInst.refChildClassMethod(ClassB.class);
    }
    /**
     * classA
     * @author WangYanCheng
     * @version 2010-8-5
     */
    class ClassA {
        /**
         * sayHello
         */
        protected void sayHello() {
            System.out.println("HelloWorld!I'm classA.");
        }
    }
    /**
     * ClassB
     * @author WangYanCheng
     * @version 2010-8-5
     */
    class ClassB extends ClassA {
        /**
         * sayGoodBye
         */
        protected void sayGoodBye() {
            System.out.println("HelloWorld!I'm classB.");
        }
    }
}
