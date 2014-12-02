package org.ybygjy.basic.algorithms.array;
/**
 * 排序数组,二分查找,线性查找
 * @author WangYanCheng
 * @version 2011-8-3
 */
public class OrderedArray {
    public static void main(String[] args) {
        int maxSize = 100;
        OrderArray orderArray = new OrderArray(maxSize);
        orderArray.insert(77);
        orderArray.insert(99);
        orderArray.insert(44);
        orderArray.insert(33);
        orderArray.insert(55);
        orderArray.insert(22);
        orderArray.insert(88);
        orderArray.insert(11);
        orderArray.insert(00);
        orderArray.insert(66);
        int searchKey = 55;
        if (orderArray.find(searchKey) != -1) {
            System.out.println("Found " + searchKey);
        } else {
            System.out.println("Not Found.");
        }
        orderArray.display();
        orderArray.delete(55);
        orderArray.delete(33);
        orderArray.delete(77);
        orderArray.display();
    }
}

class OrderArray {
    private long[] a;
    private int nElems;
    public OrderArray(int max) {
        a = new long[max];
        nElems = 0;
    }
    public int size() {
        return this.nElems;
    }
    public int find(long searchKey) {
        int lower = 0;
        int upper = nElems - 1;
        int curr = 0;
        while (true) {
            curr = (lower + upper ) / 2;
            if (a[curr] == searchKey) {
                return curr;
            } else if (lower > upper){
                return -1;
            } else {
                if (a[curr] > searchKey) {
                    upper = curr - 1;
                } else {
                    lower = curr + 1;
                }
            }
        }
    }
    public void insert(long value) {
        int j = 0;
        for (; j < nElems; j++) {
            if (a[j] > value) {
                break;
            }
        }
        for (int k = nElems; k > j; k--) {
            a[k] = a[k - 1];
        }
        a[j] = value;
        nElems++;
    }
    public boolean delete(long value) {
        int j = find(value);
        if (j == -1) {
            return false;
        } else {
            for (int k = j; k < nElems; k++) {
                a[k] = a[k + 1];
            }
            nElems --;
            return true;
        }
    }
    public void display() {
        for (int i = 0; i < nElems; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}