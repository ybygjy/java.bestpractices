package org.ybygjy.interview;

public class ClassC {
    public static void main(String[] args) {
        // System.out.println(ClassB.serialNum + ":" + ClassA.serialNum);
        // ClassB classB = new ClassB();
        // classB.toString();
        ClassN classN = new ClassN();
        System.out.println(classN);
        new ClassC().rtnTrue(classN);
        System.out.println(classN);
    }
    public boolean rtnTrue(ClassN str) {
        str = new ClassN();
        System.out.println(str);
        return true;
    }
}
class ClassN {
}