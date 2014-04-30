package org.ybygjy.basic.algorithms.sort;

/**
 * 快速排序
 * <p>1、划分思想</p>
 * <p>2、递归</p>
 * @author WangYanCheng
 * @version 2014-4-29
 */
public class QuickSort {
    private int[] dataArr;
    public QuickSort(int[] dataArr) {
        this.dataArr = dataArr;
    }
    public void doSort() {
        innerSort(0, dataArr.length - 1);
    }
    /**
     * 排序
     * <p>递归调用</p>
     * @param left
     * @param right
     */
    private void innerSort(int left, int right) {
        if (right - left <=0) {
            return;
        } else {
            int pivot = dataArr[right];//取最右端值做为枢纽
            System.out.println("划分：left:" + left + ",right:" + right + ",pivot:" + pivot);
            int partition = partitionIt(left, right, pivot);//划分
            System.out.println("左区域排序：left:" + left + ",right:" + (partition - 1));
            innerSort(left, partition - 1);//左边区域排序
            System.out.println("右区域排序：left:" + (partition + 1) + ",right:" + right );
            innerSort(partition + 1, right);//右边区域排序
        }
    }
    /**
     * 划分
     * @param left
     * @param right
     * @param pivot 枢纽
     * @return
     */
    private int partitionIt(int left, int right, int pivot) {
        int leftPtr = left - 1;
        int rightPtr = right;
        while (true) {
            while (dataArr[++leftPtr] < pivot) {
                ;
            }
            while (rightPtr > 0 && dataArr[--rightPtr] > pivot) {
                ;
            }
            if (leftPtr >= rightPtr) {
                break;
            } else {
                swap(leftPtr, rightPtr);
            }
        }
        swap(leftPtr, right);
doPrint();
        return leftPtr;
    }
    /**
     * 交换
     * @param leftPtr
     * @param rightPtr
     */
    private void swap(int leftPtr, int rightPtr) {
        int tmpValue = dataArr[leftPtr];
        dataArr[leftPtr] = dataArr[rightPtr];
        dataArr[rightPtr] = tmpValue;
    }
    /**
     * 打印当前数组元素
     */
    public void doPrint() {
        System.out.println(java.util.Arrays.toString(dataArr));
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int[] dataArr = new int[10];
        for (int i = 0; i < dataArr.length; i++) {
            dataArr[i] = (int) (Math.random() * 100);
        }
        dataArr = new int[]{2, 61, 27, 21, 4, 1, 74, 40, 90, 33};
        QuickSort qsInst = new QuickSort(dataArr);
        qsInst.doPrint();
        qsInst.doSort();
        qsInst.doPrint();
    }
}
