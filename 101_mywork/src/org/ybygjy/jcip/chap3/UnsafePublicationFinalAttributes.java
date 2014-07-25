package org.ybygjy.jcip.chap3;

/**
 * 验证因构造函数未执行完成，类包含final变量就已经被发布，导致在并发情况下会出现的Bug
 * 参考资料
 * <ol>
 * <li><a href="http://docs.oracle.com/javase/specs/jls/se5.0/html/memory.html#65124/memory.html#17.5">Java Language Specification</a>
 * <li><a href="http://stackoverflow.com/questions/3705425/java-reference-escape">StackOverflow</a></li>
 * </ol>
 * 验证结果
 * <p>1、未能复现问题</p>
 * <p>2、这块和类加载初始化顺序有联系，但我们这个变量并非是static变量，所以该变量的初始时机应该在构造函数之前</p>
 * @author WangYanCheng
 * @version 2014-7-22
 */
public class UnsafePublicationFinalAttributes {
    public static void main(String[] args) {
        final InnerUnsafe[] leak = new InnerUnsafe[1];
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new InnerUnsafe(leak);
            }
        }).start();
        while (true) {
            if (leak[0] != null) {
                if (leak[0].foo == 42) {
//                    System.out.println("OK");
                } else {
                    System.out.println("Error");
                }
            }
        }
    }
}
class InnerUnsafe {
    public final int foo = 42;
    {
        System.out.println("实例级别域初始化。");
    }
    public InnerUnsafe (InnerUnsafe[] upfaInst) {
        System.out.println("构造函数Begin");
        upfaInst[0] = this; //unsafe
        for (int i = 0; i < 20 * 1000000; i++) {
            doSomething();
        }
        System.out.println("构造函数End");
    }
    static void doSomething() {
    }
}
