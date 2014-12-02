package org.ybygjy.pattern.command.c01;

/**
 * 客户端
 * @author WangYanCheng
 * @version 2012-12-10
 */
public class Client {
    public static void main(String[] args) {
        //命令的接收者
        Receiver receiver = new Receiver();
        //命令
        Command command = new ConcreateCommand(receiver);
        //命令的调用者
        Invoker invoker = new Invoker(command);
        invoker.invoke();
    }
}
