package org.ybygjy.basic.thinking.thread;

/**
 * {@link Thread#join()}
 * @author WangYanCheng
 * @version 2010-9-27
 */
public class Thread_Join_01 extends Thread {
    /** threadCount */
    private static int threadCount = 0;
    /** duration */
    private int duration;

    /**
     * Constructor
     * @param sleepTime sleepTime
     */
    public Thread_Join_01(int sleepTime) {
        setName("" + ++threadCount);
        this.duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(this.duration);
        } catch (InterruptedException interE) {
            System.out.println(interE.getMessage());
        }
        System.out.println(this);
    }

    /**
     * getJoiner
     * @return joiner joiner
     */
    public Joiner getJoiner() {
        return new Joiner(this);
    }

    @Override
    public String toString() {
        return getName() + " was interrupted. isInterrupted():" + isInterrupted();
    }

    /**
     * Joiner
     * @author WangYanCheng
     * @version 2010-9-27
     */
    public class Joiner extends Thread {
        /** inner instance */
        private Thread_Join_01 sleeper;

        /**
         * Constructor
         * @param tjInst tjInst
         */
        public Joiner(Thread_Join_01 tjInst) {
            this.sleeper = tjInst;
            start();
        }

        @Override
        public void run() {
            try {
                sleeper.join();
            } catch (InterruptedException interE) {
                interE.printStackTrace();
            }
            System.out.println(this);
        }

        @Override
        public String toString() {
            return getName() + " join Completed#" + this.sleeper.toString();
        }
    }
}
