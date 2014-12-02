package org.ybygjy.basic.algorithms.queue;

/**
 * 后缀表达式求值
 * <p>
 * 1、利用Infix.java将中缀表达式转换成后缀表达式
 * </p>
 * <p>
 * 2、解析后缀表达式求出实际值
 * </p>
 * @author WangYanCheng
 * @version 2011-8-31
 */
public class Postfix {
    public static void main(String[] args) {
        String postfix = "235*+";
        Postfix postFix = new Postfix();
        Postfix.ParsePost parsePost = postFix.new ParsePost(postfix);
        System.out.println(postfix + "=" + parsePost.doParse());
    }

    class StackX {
        private int[] stackArr;
        private int maxSize;
        private int top;

        public StackX(int size) {
            maxSize = size;
            top = -1;
            stackArr = new int[size];
        }

        public void push(int str) {
            if ((top + 1) == maxSize) {
                throw new RuntimeException("栈满");
            }
            stackArr[++top] = str;
        }

        public int pop() {
            return stackArr[top--];
        }

        public boolean isEmpty() {
            return (top == -1);
        }

        public int peek() {
            return stackArr[top];
        }

        public int size() {
            return top + 1;
        }

        public void displayStack() {
            for (int i = 0; i < size(); i++) {
                System.out.print(stackArr[i] + ",");
            }
            System.out.println();
        }
    }

    class ParsePost {
        private StackX stackX;
        private String input;

        public ParsePost(String s) {
            input = s;
        }

        public int doParse() {
            stackX = new StackX(input.length() / 2 + 1);
            char ch;
            int num1;
            int num2;
            int interAns;
            for (int j = 0; j < input.length(); j++) {
                ch = input.charAt(j);
                if (ch >= '0' && ch <= '9') {
                    stackX.push((int) (ch - '0'));
                } else {
                    num2 = stackX.pop();
                    num1 = stackX.pop();
                    switch (ch) {
                        case '+':
                            interAns = num1 + num2;
                            break;
                        case '-':
                            interAns = num1 - num2;
                            break;
                        case '*':
                            interAns = num1 * num2;
                            break;
                        case '/':
                            interAns = num1 / num2;
                            break;
                        default:
                            interAns = 0;
                            break;
                    }
                    stackX.push(interAns);
                }
            }
            return stackX.pop();
        }
    }
}
