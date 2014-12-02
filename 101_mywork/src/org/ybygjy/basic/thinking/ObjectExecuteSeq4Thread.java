package org.ybygjy.basic.thinking;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象调用顺序
 * @author WangYanCheng
 * @version 2010-3-20
 * <br>
 * <i>结论:</i>线程在构建方法中的启用顺序非常关键，因为线程已经脱离了实例构造的生命周期
 */
public class ObjectExecuteSeq4Thread {
    /**
     * execute portal
     * @param args args
     */
    public static void main(String[] args) {
        new ObjectExecuteSeq4Thread.InnerTestClass(new ArrayList());
    }

    /**
     * InnerTestClass
     * @author WangYanCheng
     * @version 2010-3-20
     */
    static class InnerTestClass implements Runnable {
        List<String> filePaths = null;
        private boolean runFlag = true;
        /**
         * Constructor
         * @param filePaths filePaths
         */
        public InnerTestClass(List<String> filePaths) {
            if (null != filePaths) {
                new Thread(this).start();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            this.filePaths = filePaths;
            System.out.println("Contructor method.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            this.runFlag = false;
        }

        /**
         * {@inheritDoc}
         */
        public void run() {
            while (runFlag) {
                System.out.println("Invoked Run Method%d" + this.filePaths);
            }
        }
    }
}
