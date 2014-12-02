package org.ybygjy.basic.thinking.innerclass;
/**
 * 测试内部类引用外部类实例
 * @author WangYanCheng
 * @version 2010-6-7
 */
public class InnerClassRefForginClass {
    class Contents {
        private int i = 11;
        public int value() {
            return i;
        }
    }
    class Destination {
        private String label;
        Destination(String whereTo) {
            this.label = whereTo;
        }
        String readLabel() {
            System.out.println("This:" + InnerClassRefForginClass.this);
            System.out.println("Class:" + InnerClassRefForginClass.class);
            return label;
        }
    }
    public static void main(String[] args) {
        InnerClassRefForginClass icrfcInst = new InnerClassRefForginClass();
        InnerClassRefForginClass.Contents innerContentInst = icrfcInst.new Contents();
        InnerClassRefForginClass.Destination innerDestInst = icrfcInst.new Destination("Hi InnerClassInstance.");
        System.out.println("innerContentInst.value:" + innerContentInst.value());
        System.out.println(innerDestInst.readLabel());
    }
}
