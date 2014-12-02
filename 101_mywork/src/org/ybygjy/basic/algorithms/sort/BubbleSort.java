package org.ybygjy.basic.algorithms.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author WangYanCheng
 * @version 2011-8-3
 */
public class BubbleSort {
    /**
     * 排序
     * <p>左边先有序，遍历数组找最小值，严格意义上讲这不是冒泡排序</p>
     * @param array 参与排序的数组
     * @return 排序完成的数组
     */
    public int[] sortA(int[] a) {
        int[] array = a;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
        return array;
    }
    /**
     * 思路清晰
     * @param a 参与排序的数组
     * @return 排序完成的数组
     */
    public int[] sortB(int[] a) {
        int[] array = a;
        for (int i = array.length - 1; i > 1; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[i]) {
                    int tmp = array[j];
                    array[j] = array[i];
                    array[i] = tmp;
                }
            }
        }
        return array;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int[] arr = {0,2,1,3,7,7,1,5,4,6};
        BubbleSort bubbleSort = new BubbleSort();
        System.out.println(Arrays.toString(bubbleSort.sortA(arr)));
        System.out.println(Arrays.toString(bubbleSort.sortB(new int[]{4,3,2,1})));
    }
}
