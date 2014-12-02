package org.ybygjy.basic.algorithms.sort;

import java.util.Arrays;

/**
 * 插入排序
 * @author WangYanCheng
 * @version 2011-8-4
 */
public class InsertSort {
    /**
     * 排序
     * @param a 参与排序的数组
     * @return 排序完成后的数组
     */
    public int[] sortA(int[] a) {
        int in;
        int out;
        for (out = 1; out < a.length; out++) {
            int temp = a[out];
            in = out;
            while (in > 0 && a[in - 1] > temp) {
                a[in] = a[in - 1];
                in --;
            }
            a[in] = temp;
        }
        return a;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int[] a = {99,77,66,33,44,88,22,55,11, 66};
        InsertSort isInst = new InsertSort();
        System.out.println(Arrays.toString(isInst.sortA(a)));
    }
}
