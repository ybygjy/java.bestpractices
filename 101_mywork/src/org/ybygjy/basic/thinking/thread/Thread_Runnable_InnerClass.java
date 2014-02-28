package org.ybygjy.basic.thinking.thread;
/**
 * {@link Runnable}
 * @author WangYanCheng
 * @version 2010-9-27
 */
public class Thread_Runnable_InnerClass {
    /**
     * doTest
     */
    public void doTest() {
        new InnerThread1("InnerThread_1");
        new InnerThread2("InnerThread_2");
        new InnerRunnable1("InnerRunnable1");
        new InnerRunnable2("InnerRunnable2");
        new ThreadMethod("ThreadMethod").runThread();
    }
}

/**
 * InnerThread1
 * @author WangYanCheng
 * @version 2010-9-27
 */
class InnerThread1 {
    /** countDown */
    private int countDown = 5;
    /** inner */
    private Inner inner;

    /**
     * Constructor
     * @param name name
     */
    public InnerThread1(String name) {
        inner = new Inner(name);
    }

    /**
     * Inner
     * @author WangYanCheng
     * @version 2010-9-27
     */
    private class Inner extends Thread {
        /**
         * Constructor
         * @param name name
         */
        Inner(String name) {
            super(name);
            start();
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(this);
                if (--countDown == 0) {
                    return;
                }
                try {
                    sleep(10);
                } catch (InterruptedException interrE) {
                    interrE.printStackTrace();
                }
            }
        }
    }
}

/**
 * InnerThread
 * @author WangYanCheng
 * @version 2010-9-27
 */
class InnerThread2 {
    /** countDown */
    private int countDown = 5;
    /** t */
    private Thread t;

    /**
     * Constructor
     * @param name name
     */
    public InnerThread2(String name) {
        t = new Thread(name) {
            @Override
            public void run() {
                while (true) {
                    System.out.println(this);
                    if (--countDown == 0) {
                        return;
                    }
                    try {
                        sleep(10);
                    } catch (InterruptedException interrE) {
                        interrE.printStackTrace();
                    }
                }
            }

            @Override
            public String toString() {
                return getName() + ": " + countDown;
            }
        };
        t.start();
    }
}

/**
 * InnerRunnable
 * @author WangYanCheng
 * @version 2010-9-27
 */
class InnerRunnable1 {
    /** countDown */
    private int countDown = 5;
    /** InnerCompiler#instance */
    private Inner inner;

    /**
     * Constructor
     * @param name name
     */
    public InnerRunnable1(String name) {
        inner = new Inner(name);
    }

    /**
     * InnerCompiler
     * @author WangYanCheng
     * @version 2010-9-27
     */
    private class Inner implements Runnable {
        /** innerThreadInstance */
        Thread t;

        /**
         * Constructor
         * @param name name
         */
        Inner(String name) {
            t = new Thread(this, name);
            t.start();
        }

        /**
         * {@inheritDoc}
         */
        public void run() {
            while (true) {
                System.out.println(this);
                if (--countDown == 0) {
                    return;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException interE) {
                    interE.printStackTrace();
                }
            }
        }

        @Override
        public String toString() {
            return t.getName() + ": " + countDown;
        }
    }
}

/**
 * InnerRunnable
 * @author WangYanCheng
 * @version 2010-9-27
 */
class InnerRunnable2 {
    /** countDown */
    private int countDown = 5;
    /** threadInstance */
    private Thread t;

    /**
     * Constructor
     * @param name name
     */
    public InnerRunnable2(String name) {
        t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(this);
                    if (--countDown == 0) {
                        return;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException interrE) {
                        throw new RuntimeException(interrE);
                    }
                }
            }
            @Override
            public String toString() {
                return Thread.currentThread().getName() + ": " + countDown;
            }
        }, name);
        t.start();
    }
}
/**
 * ThreadMethod
 * @author WangYanCheng
 * @version 2010-9-27
 */
class ThreadMethod {
    /**countDown*/
    private int countDown = 5;
    /**current thread instance*/
    private Thread t;
    /**thread's name*/
    private String name;
    /**
     * Constructor
     * @param name thread's name
     */
    public ThreadMethod(String name) {
        this.name = name;
    }
    /**
     * runThreadMethod
     */
    public void runThread() {
        if (t == null) {
            t = new Thread(name) {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(this);
                        if (--countDown == 0) {
                            return;
                        }
                        try {
                            sleep(10);
                        } catch (InterruptedException interrE) {
                            throw new RuntimeException(interrE);
                        }
                    }
                }
                @Override
                public String toString() {
                    return getName() + ": " + countDown;
                }
            };
        }
    }
}
