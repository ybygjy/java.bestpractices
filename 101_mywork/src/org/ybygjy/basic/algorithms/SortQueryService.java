package org.ybygjy.basic.algorithms;

import java.util.Arrays;

public class SortQueryService {
    public int[] minK(int[] sarr, int k) {
        int[] arr = new int[k];
        for (int i = 0; i < k; i++) {
            arr[i] = sarr[i];
        }
        buildHeap(arr);
        for (int j = k; j < sarr.length; j++) {
            if (sarr[j] < arr[0]) {
                arr[0] = sarr[j];
                maxHeap(arr, 1, k);
            }
        }
        return arr;
    }

    public void buildHeap(int[] arr) {
        int heapSize = arr.length;
        for (int i = arr.length / 2; i > 0; i--) {
            maxHeap(arr, i, heapSize);
        }
    }

    public void maxHeap(int[] arr, int i, int heapSize) {
        int largest = i;
        int left = 2 * i;
        int right = 2 * i + 1;
        if (left <= heapSize && arr[i - 1] < arr[left - 1]) {
            largest = left;
        }
        if (right <= heapSize && arr[largest - 1] < arr[right - 1]) {
            largest = right;
        }
        if (largest != i) {
            int tmpValue = arr[i - 1];
            arr[i - 1] = arr[largest - 1];
            arr[largest - 1] = tmpValue;
            maxHeap(arr, largest, heapSize);
        }
    }

    public static void main(String[] args) {
        SortQueryService sortQueryService = new SortQueryService();
        int[] sarr = { 7, 8, 1, 3, 4, 2, 9, 10, 14, 16 };
        int[] arr = sortQueryService.minK(sarr, 4);
        System.out.println(Arrays.toString(arr));
    }
}
