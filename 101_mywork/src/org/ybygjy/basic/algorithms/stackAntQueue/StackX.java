package org.ybygjy.basic.algorithms.stackAntQueue;

/**
 * 栈
 * <p>后进先出(LIFO)</p>
 * @author WangYanCheng
 * @version 2011-8-5
 */
public class StackX {
    private int maxSize;
    private int[] stackArray;
    private int top;

    /**
     * Constructor
     * @param size size
     */
    public StackX(int size) {
        this.maxSize = size;
        this.stackArray = new int[maxSize];
        this.top = -1;
    }

    /**
     * 出栈
     * @return rtnValue
     */
    public int pop() {
        return this.stackArray[top--];
    }

    /**
     * 入栈
     * @param i rtnValue
     */
    public void push(int i) {
        this.stackArray[++top] = i;
    }

    /**
     * 判断栈满
     * @return true/false
     */
    public boolean isFull() {
        return (top == (this.maxSize - 1));
    }

    /**
     * 判断栈空
     * @return true/false
     */
    public boolean isEmpty() {
        return (this.top == -1);
    }

    /**
     * 查看当前栈顶数据项
     * @return 数据项
     */
    public int peek() {
        return this.stackArray[top];
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        StackX stackX = new StackX(10);
        stackX.push(20);
        stackX.push(40);
        stackX.push(60);
        stackX.push(80);
        while (!stackX.isEmpty()) {
            System.out.print(stackX.pop() + ",");
        }
    }
}
