package org.ybygjy.basic.algorithms;

public class Test {
    public static void main(String[] args) {
        Test.doPrintRA(5);
        Test.doPrintES(5);
        Test.doPrintMT(9);
    }
    /**
     * 打印直角
     * @param r 层数
     */
    public static void doPrintRA(int r) {
        for (int i = 0; i < r; i++) {
            for (int j = i + 1; j > 0; j--) {
                System.out.print(((j - 1) == 0) ? "*" : "* ");
            }
            System.out.println();
        }
    }
    /**
     * 打印等边
     * @param r 层数
     */
    public static void doPrintES(int r) {
        for (int j = 0; j < r; j++) {
            int t = r - j;
            while (t > 1) {
                System.out.print(" ");
                t--;
            }
            for (int k = j + 1; k > 0; k--) {
                System.out.print((k - 1 == 0) ? "*" : "* ");
            }
            System.out.println();
        }
    }
    /**
     * 打印乘法表
     * @param b 层数
     */
    public static void doPrintMT(int b) {
        for (int i = 1; i <=b; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + (j*i) + " ");
            }
            System.out.println();
        }
    }
}
