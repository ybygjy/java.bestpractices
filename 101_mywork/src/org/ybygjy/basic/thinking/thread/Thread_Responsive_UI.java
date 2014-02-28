package org.ybygjy.basic.thinking.thread;

/**
 * ThreadResponsiveUI
 * @author WangYanCheng
 * @version 2010-9-27
 */
public class Thread_Responsive_UI {
    /**
     * doTest
     * @throws Exception Exception
     */
    public void doTest() throws Exception {
        //new UnResponsiveUI();
        ResponsiveUI ruiInst = new ResponsiveUI();
        System.out.println("Please Input a byte;");
        System.in.read();
        System.out.println("Arithmetic Result : " + ruiInst.d);
    }

    /**
     * UnResponsiveUI
     * @author WangYanCheng
     * @version 2010-9-27
     */
    public class UnResponsiveUI {
        /** volatile variable */
        private volatile double d = 1;

        /**
         * Constructor
         * @throws Exception Exception
         */
        public UnResponsiveUI() throws Exception {
            while (d > 0) {
                d = d + (Math.PI + Math.E) / d;
            }
            // Unreachable code
            System.in.read();
        }
    }

    /**
     * ResponsiveUI
     * @author WangYanCheng
     * @version 2010-9-27
     */
    private class ResponsiveUI extends Thread {
        /** volatile variable */
        private volatile double d = 1;

        /**
         * Constructor
         */
        public ResponsiveUI() {
            setDaemon(true);
            start();
        }

        @Override
        public void run() {
            while (true) {
                d = d + (Math.PI + Math.E) / d;
            }
        }
    }
}
