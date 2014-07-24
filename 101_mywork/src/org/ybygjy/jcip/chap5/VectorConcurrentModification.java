package org.ybygjy.jcip.chap5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * JCIP#5002_对容器进行迭代操作的并发问题
 * @author WangYanCheng
 * @version 2014-7-24
 */
public class VectorConcurrentModification {
    public static void main(String[] args) {
        List<String> dataList = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 0; i < 10; i++) {
            dataList.add("List#" + i);
        }
        new IteratorThread("IteratorThread", dataList).start();
    }
    static class IteratorThread extends Thread {
        private Iterable<String> iterableInstance;
        public IteratorThread(String threadName, Iterable<String> iterableInstance) {
            super(threadName);
            this.iterableInstance = iterableInstance;
        }
        public void run() {
            while (true) {
                Iterator<String> iterator = this.iterableInstance.iterator();
                while (iterator.hasNext()) {
                    System.out.println(getName() + ":" + iterator.next());
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    interrupt();
                }
            }
        }
    }
    static class ModifyThread extends Thread {
        private Iterable<String> iterable;
        public ModifyThread(Iterable<String> iterable) {
            this.iterable = iterable;
        }
        public void run() {
            while (true) {
            }
        }
    }
}
