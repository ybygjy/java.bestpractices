package org.ybygjy.interview;

public class ClassB extends ClassA {
    public static double serialNum = Math.random();
    public String userName = null;
    static {
        serialNum = Math.random();
    }
    public ClassB() {
        userName = "王延成";
    }
}
