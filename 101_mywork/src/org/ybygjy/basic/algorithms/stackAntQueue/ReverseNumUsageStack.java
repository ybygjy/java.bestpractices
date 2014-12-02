package org.ybygjy.basic.algorithms.stackAntQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 栈应用，反转输入
 * @author WangYanCheng
 * @version 2011-8-8
 */
public class ReverseNumUsageStack {
    private Stack stack = null;
    public ReverseNumUsageStack() {
        this.stack = new Stack(100);
    }
    public void doWork() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            System.out.println("请输入：");
            while ((s = reader.readLine())!=null) {
                if ("exit".equals(s)) {
                    while (!this.stack.isEmpty()) {
                        System.out.println(this.stack.pop() + "\t");
                        break;
                    }
                } else {
                    this.stack.push(s);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ReverseNumUsageStack rnusInst = new ReverseNumUsageStack();
        rnusInst.doWork();
    }
    class Stack {
        private int top;
        private int max;
        private String[] arr;
        public Stack(int max) {
            this.max = max;
            arr = new String[max];
            top = -1;
        }
        public void push(String s) {
            if (!ifFull()) {
                arr[++top] = s;
            } else {
                System.out.println("栈满==>" + this.max);
            }
        }
        public String pop() {
            if (!isEmpty()) {
                return arr[top--];
            } else {
                System.out.println("栈空==>" + this.top);
                return null;
            }
        }
        public boolean ifFull() {
            return (this.max - 1) == this.top;
        }
        public boolean isEmpty() {
            return -1 == this.top;
        }
    }
}