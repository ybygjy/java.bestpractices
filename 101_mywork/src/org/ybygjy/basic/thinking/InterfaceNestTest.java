package org.ybygjy.basic.thinking;
/**
 * 嵌套接口测试
 * @author WangYanCheng
 * @version 2010-5-31
 */
public class InterfaceNestTest {
    public class BImp implements A.B {
        public void f() {
            System.out.println("InterfaceNestTest==>BImp==>f()");
        }
    }
    class CImp implements A.C {
        public void f() {
            System.out.println("InterfaceNestTest==>CImp==>f()");
        }
    }
    class EImp implements E {
        public void g() {
            System.out.println("EImp");
        }
    }
    class BGImp implements E.G {
        public void f() {
            System.out.println("BGImp implements E.G");
        }
    }
    class EImp2 implements E {
        public void g() {
            System.out.println("EImp2 implements g()");
        }
        class EG implements E.G {
            public void f() {
                System.out.println("EG implements f()");
            }
        }
    }
    public static void main(String[] args) {
        A a = new A();
        Object tmpObj = a.getD();
        System.out.println(tmpObj);
        A a2 = new A();
        a2.receiveD(a.getD());
    }
}
class A {
    interface B {
        void f();
    }
    public class BImp implements B {
        public void f() {
            System.out.println("BImp....");
        }
    }
    private class BImp2 implements B {
        public void f() {
            System.out.println("BImp2....");
        }
    }
    public interface C {
        void f();
    }
    class CImp implements C {
        public void f() {
            System.out.println("CImp....");
        }
    }
    private class CImp2 implements C {
        public void f() {
            System.out.println("CImp2.....");
        }
    }
    private interface D {
        void f();
    }
    private class DImp implements D {
        public void f() {
            System.out.println("DImp....");
        }
    }
    public class DImp2 implements D {
        public void f() {
            System.out.println("DImp2.....");
        }
    }
    public D getD() {
        return new DImp2();
    }
    private D dRef;
    public void receiveD(D d) {
        dRef = d;
        dRef.f();
    }
}
interface E {
    interface G {
        void f();
    }
    public interface H {
        void f();
    }
    void g();
//    private interface I {}
}
