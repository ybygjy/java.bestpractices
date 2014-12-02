package org.ybygjy.basic.algorithms.queue;


/**
 * 优先级队列
 * @author WangYanCheng
 * @version 2011-8-14
 */
public class PriorityQueue {
    private int maxSize;
    private int[] arr;
    private int nItems;
    public PriorityQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[this.maxSize];
        this.nItems = 0;
    }
    public void insert(int item) {
        int j = 0;
        if (nItems == 0) {
            arr[nItems++] = item;
        } else {
            for (j= nItems - 1; j >= 0; j--) {
                if (item > arr[j]) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = item;
            nItems ++;
        }
    }
    /**
     * 删除(出队)
     * @return 数据项
     */
    public int remove() {
        return arr[--nItems];
    }
    /**
     * 查当前最小值
     * @return 数据项
     */
    public int peekMin() {
        return arr[nItems - 1];
    }
    public boolean isEmpty() {
        return 0 == nItems;
    }
    public boolean isFull() {
        return maxSize == nItems;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        PriorityQueue pqInst = new PriorityQueue(6);
        pqInst.insert(10);
        pqInst.insert(30);
        pqInst.insert(20);
        pqInst.insert(40);
        pqInst.insert(50);
        while (!pqInst.isEmpty()) {
            System.out.print(pqInst.remove() + ", ");
        }
    }
}
