package org.ybygjy.basic.thinking.innerclass;
/**
 * 内部类继承测试
 * <i>结论及注意事项:</i>
 * <li>1、继承嵌套内部类,注意构造初始化问题</li>
 * <li>2、子类不能有无参构造</li>
 * <li>3、其实子类继承的某个类的内部类需要引含着对包含它的外部类的引用，
 * 并且这个引用需要初始化(注意传递WithInner引用)</li>
 * @author WangYanCheng
 * @version 2010-6-7
 */
public class InheritInner extends WithInner.Inner {
    /*public InheritInner() {
    }*/
    public InheritInner(WithInner wi) {
        wi.super();
        System.out.println("Hello InheritInnerClass");
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        //new InheritInner(new WithInner());
        WithInner wi = new WithInner();
    }
}
class WithInner {
    public WithInner() {
        System.out.println("Hello WithInnerClass.");
    }
    class Inner {
        Inner() {
            System.out.println("Hello super InnerCompiler.");
        }
    }
}
