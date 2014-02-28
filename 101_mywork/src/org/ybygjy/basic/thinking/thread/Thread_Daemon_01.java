package org.ybygjy.basic.thinking.thread;
/**
 * DaemonThread
 * @author WangYanCheng
 * @version 2010-9-27
 */
public class Thread_Daemon_01 extends Thread {
    /**threadCount*/
    private static int threadCount = 0;
    /**
     * Constructor
     */
    public Thread_Daemon_01() {
        setName("" + ++threadCount);
        setDaemon(true);
        start();
    }
    @Override
    public void run() {
        while (true) {
            try {
                sleep(500);
            } catch (InterruptedException interE) {
                interE.printStackTrace();
            }
            System.out.println(this);
        }
    }
    @Override
    public String toString() {
        return getName();
    }
}
