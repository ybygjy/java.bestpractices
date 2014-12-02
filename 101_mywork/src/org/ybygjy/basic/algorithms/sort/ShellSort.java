package org.ybygjy.basic.algorithms.sort;

import java.util.Arrays;


/**
 * 希尔排序
 * @author WangYanCheng
 * @version 2014-3-27
 */
public class ShellSort {
    /**
     * 希尔排序
     * <p>1、间隔序列是关键</p>
     * <p>2、要求最终间隔为1（普通排入排序）</p>
     */
    public void shellSort() {
        int[] arr = new int[]{3, 5, 9, 6, 7, 2, 4, 8, 0, 1};
System.out.println("Begin=>" + Arrays.toString(arr));
        int inner, outer;
        int temp;
        int h = 1;
        //间隔序列
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (outer = h; outer < arr.length; outer ++) {
                temp = arr[outer];
                inner = outer;
                while (inner > h -1 && arr[inner - h] >= temp) {
                    arr[inner] = arr[inner - h];
                    inner -= h;
                    System.out.println(Arrays.toString(arr));
                }
                arr[inner] = temp;
            }
            //倒推减少间隔
            h = (h - 1) / 3;
        }
System.out.println("End=>" + Arrays.toString(arr));
    }
    public void calcIncreaSeq(int length) {
        int num = 1;
        while (num <= length / 3) {
            num = num * 3 + 1;
            System.out.println(num);
        }
    }
    public static void main(String[] args) {
        new ShellSort().shellSort();
    }
}
