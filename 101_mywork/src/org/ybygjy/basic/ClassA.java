package org.ybygjy.basic;


public class ClassA {
    //静态成员变量
    public static double serialNum = Math.random();
    public String userName;
    static {
        serialNum = Math.random();
    }
    public ClassA() {
        userName = "WangYanCheng";
    }
}