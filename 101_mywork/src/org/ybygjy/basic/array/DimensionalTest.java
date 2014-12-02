package org.ybygjy.basic.array;

/**
 * 多维数组
 * @author WangYanCheng
 * @version 2014-5-3
 */
public class DimensionalTest {
    /**
     * 一维数组
     */
    public void oneDimension() {
        int[] oneDimensinal = {1, 2, 3, 4};
        System.out.println();
        System.out.println("one-dimension begin");
        for (int i : oneDimensinal) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.println("one-dimension end");
    }
    /**
     * 二维数组
     */
    public void twoDimension() {
        int[][] twoDimension = {
            {11, 12, 13, 14},
            {21, 22, 23, 24},
            {31, 32}
            };
        System.out.println();
        System.out.println("two-dimension begin");
        for (int[] iArr : twoDimension) {
            for (int jArr : iArr) {
                System.out.print(jArr + "\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("one-dimension end");
    }
    /**
     * 三维数组
     */
    public void threeDimension() {
        int[][][] treeDimension = {
            {
                {111, 112, 113, 114},
                {121, 122, 123, 124},
                {131, 132, 133, 134}
            },
            {
                {211, 212, 213, 214},
                {221, 222, 223, 224},
                {231, 232, 233, 234}
            },
            {
                {311, 312, 313, 314},
                {321, 322, 323, 324},
                {331, 332, 333, 334}
            }
        };
        System.out.println();
        System.out.println("one-dimension end");
        for (int[][] iArr : treeDimension) {
            for (int [] jArr : iArr) {
                for (int kArr : jArr) {
                    System.out.print(kArr + "\t");
                }
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("one-dimension end");
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        DimensionalTest dimInst = new DimensionalTest();
        dimInst.oneDimension();
        dimInst.twoDimension();
        dimInst.threeDimension();
    }
}
