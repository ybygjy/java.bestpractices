package org.ybygjy.basic.thinking.thread.pool;

import java.util.LinkedList;

/**
 * 线程池实践
 * @author WangYanCheng
 * @version 2012-11-31
 */
public class ThreadPool_01 {
    // 有一个任务队列
    private final LinkedList<Runnable> queue;
    // 有一个工作线程数量
    private final int workerThreadNums;
    // 有一个工作线程集合
    private final ThreadWorker[] threadWorkers;
    // 实际工作线程数量
    private int thNums;

    /**
     * Constructor
     * @param workerThreadNums 初始工作线程数量
     */
    public ThreadPool_01(int workerThreadNums) {
        this.queue = new LinkedList<Runnable>();
        this.workerThreadNums = workerThreadNums;
        this.threadWorkers = new ThreadWorker[this.workerThreadNums];
        for (ThreadWorker tw : this.threadWorkers) {
            tw = new ThreadWorker(ThreadWorker.THNAMEPREFIX.concat(String.valueOf(thNums++)));
            tw.start();
        }
    }

    /**
     * 提供给客户端的API，负责接收逻辑任务，并调度执行
     * @param r 可执行任务
     */
    public void execute(Runnable r) {
        synchronized (queue) {
            queue.add(r);
            // 不存在共享资源的模式，只通知一个等待线程即可
            queue.notify();
        }
    }

    /**
     * 线程池中的工作线程，其生命周期被池对象完全控制，负责执行任务
     * @author WangYanCheng
     * @version 2012-11-31
     */
    private class ThreadWorker extends Thread {
        private static final String THNAMEPREFIX = "TW_";

        /**
         * Constructor
         * @param thName
         */
        public ThreadWorker(String thName) {
            super(thName);
        }
        @Override
        public void run() {
            Runnable run;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    run = queue.removeFirst();
                }
                // 任务逻辑处理异常一定不要影响到基础线程
                try {
                    run.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 充当客户端，负责构造可执行的任务
     * @author WangYanCheng
     * @version 2012-11-31
     */
    private class ThreadTester extends Thread {
        public ThreadTester() {
            super();
            setName("ThreadTester");
            start();
        }

        @Override
        public void run() {
            int taskCount = 1;
            while (true) {
                execute(new VirtualTask(VirtualTask.TASKPREFIX.concat(String.valueOf(taskCount++))));
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 任务实例
     * @author WangYanCheng
     * @version 2012-11-31
     */
    private class VirtualTask implements Runnable {
        private static final String TASKPREFIX = "CT_";
        /** 任务Id */
        private String taskId;

        /**
         * Constructor
         * @param taskId 任务Id
         */
        public VirtualTask(String taskId) {
            this.taskId = taskId;
        }

        /**
         * 任务逻辑入口
         */
        public void run() {
            System.out.println(this);
            try {
                Thread.sleep((long)(Math.random() * 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "VirtualTask [taskId=" + taskId + "]";
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ThreadPool_01 tp01 = new ThreadPool_01(2);
        tp01.new ThreadTester();
    }
}
