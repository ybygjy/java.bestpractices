package org.ybygjy.basic.thinking.thread;
/**
 * {@link Thread#join()}学习
 * <p>
 * {@link Thread#join()}使用场景即当主线程启用了子线程，子线程负责进行大规模计算。这时主线程需要
 * 子线程计算结果，由此得出主线程需要等待子线程运算完毕得出运算结果后，主线程下一步计算才有意义。
 * </p>
 * @author WangYanCheng
 * @version 2011-6-1
 */
public class ThreadJoin {
    public void doTest() {
        Thread tjOne = new ThreadJoinOne();
        Thread tjTwo = new ThreadJoinTwo();
        tjOne.start();
        tjTwo.start();
        try {
            tjOne.join();
            tjTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
    class ThreadJoinOne extends Thread {
        private int i = 0;
        public void run() {
            String threadName = (Thread.currentThread().getName());
            while (i < 5) {
                i++;
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(threadName + "  over。。。");
        }
    }
    class ThreadJoinTwo extends Thread {
        private int i = 0;
        public void run() {
            String threadName = (Thread.currentThread().getName());
            while (i < 5) {
                i++;
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(threadName + "  over。。。");
        }
    }
    public static void main(String[] args) {
        ThreadJoin threadJoin = new ThreadJoin();
        threadJoin.doTest();
    }
}