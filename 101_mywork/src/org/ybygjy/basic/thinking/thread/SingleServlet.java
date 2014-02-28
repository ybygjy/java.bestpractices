package org.ybygjy.basic.thinking.thread;

/**
 * SingleServlet
 * @author WangYanCheng
 * @version 2011-4-7
 */
public class SingleServlet {
    public static void main(String[] args) {
        Service service = new Service();
        new WorkerThread(service);
        new WorkerThread(service);
    }
}
class Service {
    private int i;
    public synchronized void service(String threadName) {
        System.out.println(threadName + "\t" + i);
        try {
            i++;
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class WorkerThread extends Thread {
    private Service service;
    public WorkerThread(Service service) {
        this.service = service;
        start();
    }
    public void run() {
        while (true) {
            service.service(currentThread().getName());
        }
    }
}