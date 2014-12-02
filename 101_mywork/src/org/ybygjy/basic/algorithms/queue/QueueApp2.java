package org.ybygjy.basic.algorithms.queue;

/**
 * <p>
 * 利用front与rear通过计算得出队列的状态以及队列元素个数
 * </p>
 * <p>
 * 不使用数据项计数字段实现队列
 * </p>
 * <p>
 * 约定少用一个队列空间标记队列满
 * </p>
 * @author WangYanCheng
 * @version 2011-8-9
 */
public class QueueApp2 {
    private Queue queue;
    public QueueApp2() {
        queue = new Queue(11);
    }
    public void doWork() {
        queue.insert(0);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(4);
        queue.insert(5);
        queue.insert(6);
        queue.insert(7);
        queue.insert(8);
        queue.insert(9);
        queue.insert(-1);
        queue.remove();
        queue.remove();
        queue.insert(10);
        queue.insert(11);
        while (!queue.isEmpty()) {
            System.out.print(queue.remove() + ", ");
        }
    }
    public static void main(String[] args) {
        new QueueApp2().doWork();
    }
    class Queue {
        private int maxSize;
        private int[] arr;
        private int front;
        private int rear;

        public Queue(int maxSize) {
            this.maxSize = maxSize;
            arr = new int[this.maxSize];
            front = 0;
            rear = 0;
        }

        public void insert(int a) {
            if (isFull()) {
                System.out.println("队列满==>" + rear + ":" + front);
                return;
            }
            arr[rear] = a;
            rear = (rear + 1) % maxSize;
        }

        public int remove() {
            if (isEmpty()) {
                throw new RuntimeException("队列空");
            }
            int tmp = arr[front];
            front = (front + 1) % maxSize;
            return tmp;
        }

        public int peek() {
            return arr[front];
        }

        public boolean isEmpty() {
            return this.rear == this.front;
        }

        public boolean isFull() {
            return (this.rear + 1) % maxSize == this.front;
        }

        public int size() {
            return (rear - front + maxSize) % maxSize;
        }
    }
}
