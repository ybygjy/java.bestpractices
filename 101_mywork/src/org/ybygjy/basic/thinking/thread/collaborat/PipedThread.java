package org.ybygjy.basic.thinking.thread.collaborat;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;

import org.ybygjy.basic.thinking.thread.testframework.Timeout;

/**
 * 线程主题_内置线程通信管道测试
 * @author WangYanCheng
 * @version 2010-10-8
 */
public class PipedThread {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        sender.start();
        receiver.start();
        new Timeout(5000, "Terminated");
    }
}

/**
 * Sender
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Sender extends Thread {
    /** randomInst */
    private Random rand;
    /** pipedWriter */
    private PipedWriter out;

    /**
     * Constructor
     */
    public Sender() {
        rand = new Random();
        out = new PipedWriter();
    }

    /**
     * getPipedWriter refrence
     * @return the out
     */
    public PipedWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        while (true) {
            for (char c = 'A'; c <= 'z'; c++) {
                try {
                    out.write(c);
                    sleep(rand.nextInt(500));
                } catch (InterruptedException intrr) {
                    intrr.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}

/**
 * Receiver
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Receiver extends Thread {
    /** PipedReader */
    private PipedReader in;

    /**
     * Constructor
     * @param sender {@link PipedWriter}
     */
    public Receiver(Sender sender) {
        try {
            in = new PipedReader(sender.getOut());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Read: " + (char) in.read());
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
