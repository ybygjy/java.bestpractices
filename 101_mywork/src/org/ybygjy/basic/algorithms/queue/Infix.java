package org.ybygjy.basic.algorithms.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 中缀表达式
 * <p>
 * 由中缀表达式求后缀表达式，利用对算式操作符的出入栈来完成
 * </p>
 * @author WangYanCheng
 * @version 2011-8-31
 */
public class Infix {
    public static void main(String[] args) {
        Infix infix = new Infix();
        while (true) {
            System.out.println("请输入中缀表达式：");
            String inStr = getStr();
            if (null == inStr) {
                break;
            }
            InfoPost infoPost = infix.new InfoPost(inStr);
            System.out.println("中缀表达式：".concat(inStr).concat("\t后缀表达式：").concat(infoPost.doTrans()));
        }
    }

    public static String getStr() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String rtnStr = null;
        try {
            rtnStr = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rtnStr;
    }

    class StackX {
        private char[] stackArr;
        private int top;

        public StackX(int size) {
            stackArr = new char[size];
            top = -1;
        }

        public void push(char j) {
            stackArr[++top] = j;
        }

        public char pop() {
            return stackArr[top--];
        }

        public char peek() {
            return stackArr[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public int size() {
            return top + 1;
        }

        private char peekN(int n) {
            return stackArr[n];
        }

        public void displayStack(String s) {
            System.out.println(s);
            for (int j = 0; j < size(); j++) {
                System.out.print(peekN(j) + ",");
            }
            System.out.println();
        }
    }

    class InfoPost {
        private StackX theStack;
        private String inputStr;
        private String outputStr;

        public InfoPost(String in) {
            inputStr = in;
            theStack = new StackX(inputStr.length());
            outputStr = "";
        }

        public String doTrans() {
            for (int j = 0; j < inputStr.length(); j++) {
                char ch = inputStr.charAt(j);
                switch (ch) {
                    case '+':
                    case '-':
                        gotOper(ch, 1);
                        break;
                    case '*':
                    case '/':
                        gotOper(ch, 2);
                        break;
                    case '(':
                        theStack.push(ch);
                        break;
                    case ')':
                        gotParent(ch);
                        break;
                    default:
                        outputStr = outputStr + ch;
                        break;
                }
            }
            while (!theStack.isEmpty()) {
                theStack.displayStack("While");
                outputStr = outputStr + theStack.pop();
            }
            return outputStr;
        }

        public void gotOper(char opThis, int prec1) {
            while (!theStack.isEmpty()) {
                char opTop = theStack.pop();
                if (opTop == '(') {
                    theStack.push(opTop);
                    break;
                } else {
                    int prec2;
                    if (opTop == '+' || opTop == '-') {
                        prec2 = 1;
                    } else {
                        prec2 = 2;
                    }
                    if (prec1 > prec2) {
                        theStack.push(opTop);
                        break;
                    } else {
                        outputStr = outputStr + opTop;
                    }
                }
            }
            theStack.push(opThis);
        }

        public void gotParent(char ch) {
            while (!theStack.isEmpty()) {
                char chx = theStack.pop();
                if (chx == '(') {
                    break;
                } else {
                    outputStr = outputStr + chx;
                }
            }
        }
    }
}
