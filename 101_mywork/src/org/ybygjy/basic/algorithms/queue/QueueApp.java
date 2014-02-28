package org.ybygjy.basic.algorithms.queue;

/**
 * 队列
 * <p>队列的基本特性：先进先出(FIFO)原则</p>
 * <p>使用数据项计数字段实现队列</p>
 * @author WangYanCheng
 * @version 2011-8-8
 */
public class QueueApp {
    public static void main(String[] args) {
        Queue queue = new Queue(10);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(4);
        queue.insert(5);
        queue.insert(6);
        queue.insert(7);
        queue.insert(8);
        queue.insert(9);
        queue.insert(10);
        queue.insert(11);
        queue.insert(12);
        while (!queue.isEmpty()) {
            System.out.print(queue.remove() + ", ");
        }
        System.out.println();
    }
}

class Queue {
    private int[] queArr;
    private int maxSize;
    private int front;
    private int rear;
    private int nItems;

    public Queue(int s) {
        this.maxSize = s;
        this.queArr = new int[this.maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }

    /**
     * 在队尾插入元素
     * @param s 元素
     */
    public void insert(int s) {
        if (rear == maxSize - 1) { // deal with wraparound
            rear = -1;
        }
        queArr[++rear] = s; // increment rear and insert
        nItems++;
    }

    public int remove() {
        int temp = queArr[front++]; // get value and incr front
        if (front == maxSize) {
            front = 0;
        }
        nItems--;
        return temp;
    }

    public int peekFront() {
        return queArr[front];
    }

    public boolean isEmpty() {
        return nItems == 0;
    }

    public boolean isFull() {
        return nItems == maxSize;
    }

    public int size() {
        return nItems;
    }
}
