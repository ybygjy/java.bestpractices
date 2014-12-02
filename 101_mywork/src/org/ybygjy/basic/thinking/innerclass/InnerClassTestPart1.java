package org.ybygjy.basic.thinking.innerclass;
/**
 * 内部类测试
 * @author WangYanCheng
 * @version 2010-5-31
 */
public class InnerClassTestPart1 {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        InnerClass icInst = new InnerClass();
        System.out.println("icInst.cont().value()==>" + icInst.cont().value());
        System.out.println("icInst.dest==>" + icInst.dest("I'm a Developer.").readLabel());
    }
}
class InnerClass {
    private class PContents implements Destination.Contents {
        public int value() {
            return 'a';
        }
    }
    protected class PDestination implements Destination {
        Object refObj = null;
        public PDestination(Object refObj) {
            this.refObj = refObj;
        }
        public String readLabel() {
            return "readLabel==>" + refObj;
        }
    }
    public Destination dest(String s) {
        return new PDestination("Hello InnerCompiler World.. " + s);
    }
    public Destination.Contents cont() {
        return new PContents();
    }
}
interface Destination {
    String readLabel();
    interface Contents {
        int value();
    }
}
