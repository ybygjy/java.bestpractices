package org.ybygjy.basic.algorithms.sort;

import java.util.Arrays;

/**
 * 选择排序(查找排序)
 * @author WangYanCheng
 * @version 2011-8-4
 */
public class SelectSort {
    /**
     * 排序
     * @param a 参与排序的数组
     * @return 排序完成的数组
     */
    public int[] sortA(int[] a) {
        int flag = 0;
        int out = 0;
        int len = a.length;
        for(; out < len - 1; out++) {
            flag = out;
            for (int j = out + 1; j < len; j++) {
                if (a[flag] > a[j]) {
                    flag = j;
                }
            }
            if (out != flag) {
                int tmp = a[out];
                a[out] = a[flag];
                a[flag] = tmp;
            }
        }
        return a;
    }
    public int[] sortB(int[] a) {
        int out = 0;
        int in = 0;
        int min = 0;
        for (out = 0; out < a.length - 1; out++) {
            min = out;
            for (in = out + 1; in < a.length; in ++) {
                if (a[in] < a[min]) {
                    min = in;
                }
            }
            if (out != min) {
                int tmp = a[out];
                a[out] = a[min];
                a[min] = tmp;
            }
        }
        return a;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int[] a = {7, 6, 5, 3, 4, 2, 0, 8, 1};
        SelectSort ssInst = new SelectSort();
        System.out.println(Arrays.toString(ssInst.sortA(a)));
        //System.out.println(Arrays.toString(ssInst.sortB(a)));
    }
}
