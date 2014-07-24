package org.ybygjy.jcip.chap5;

import java.util.Vector;


/**
 * JCIP#5001_同步容器的复合操作会出现问题，这同样需要额外的客户端处理机制
 * <p>1、在客户端不加锁的情况下对容器类进行复合操作时会出现问题</p>
 * <p>2、此类问题的原因是客户端在对线程安全容器进行复合操作不是原子的，试想当get/set同时操作容器的情况，虽然容器内部保证了自身的线程安全性，但是外部调用者却得到了非预期的结果。</p>
 * 解决
 * <p>1、客户端加锁</p>
 * <p>2、注意客户端锁必须与容器使用的锁相同，否则没有任何意义，并且把错误隐藏的更深。</p>
 * @author WangYanCheng
 * @version 2014-7-24
 */
public class VectorComplexOperation {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i < 10; i++) {
            vector.add("VectorItem_" + (i));
        }
        new GetThread("GetThread", vector).start();
        new DeleteThread("DeleteThread", vector).start();
    }
    static class GetThread extends Thread {
        private Vector<String> vector;
        public GetThread(String threadName, Vector<String> vector) {
            super(threadName);
            this.vector = vector;
        }
        public void run() {
            while (true) {
                System.out.println(getName() + ":" + (vector.size() - 1) + "#" + vector.get(vector.size() - 1));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    interrupt();
                    e.printStackTrace();
                }
                
            }
        }
    }
    static class DeleteThread extends Thread {
        private Vector<String> vector;
        public DeleteThread(String threadName, Vector<String> vector) {
            super(threadName);
            this.vector = vector;
        }
        public void run() {
            while (true) {
                int lastIndex = this.vector.size() - 1;
                System.out.println(getName() + ":" + lastIndex + "#" + this.vector.remove(lastIndex));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
