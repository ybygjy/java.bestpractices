package org.ybygjy.jcip.chap5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;


/**
 * JCIP#5002_对容器进行迭代操作的并发问题
 * <p>容器在进行迭代期间其它线程修改了容器</p>
 * 1、解决办法:
 * <p>1.1、将对容器的迭代和修改抽象为一组复合操作，在客户端对操作进行加锁。</p>
 * <p>1.2、采用CopyOnWriter方式(克隆)，在副本上进行迭代。</p>
 * <p>1.3、注意隐式迭代问题，容器中类似toString()、hashCode()这类方法为对自身内容进行迭代。</p>
 * <p>1.4、通过引用并发容器避免此问题</p>
 * 2、基础:
 * <p>2.1、同步容器将所有对容器状态的访问都串行化，以实现它们的线程安全性。</p>
 * <p>2.2、同步容器这种锁机制严重降低了并发性，多线程竞争情况下吞吐量严重减低。</p>
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
        new UpdateThread("UpdateThread", dataList).start();
    }
    /**
     * 负责对容器进行迭代
     * @author WangYanCheng
     * @version 2014年7月25日
     */
    static class IteratorThread extends Thread {
        private Iterable<String> iterableInstance;
        public IteratorThread(String threadName, Iterable<String> iterableInstance) {
            super(threadName);
            this.iterableInstance = iterableInstance;
        }
        public void run() {
            while (true) {
                try {
                    Iterator<String> iterator = this.iterableInstance.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(getName() + ":" + iterator.next());
                    }
                } catch (ConcurrentModificationException cmeInst) {
                    cmeInst.printStackTrace();
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
    /**
     * 负责
     * @author WangYanCheng
     * @version 2014年7月25日
     */
    static class UpdateThread extends Thread {
        private List<String> dataList;
        public UpdateThread(String threadName, List<String> dataList) {
            super(threadName);
            this.dataList = dataList;
        }
        public void run() {
            while (true) {
                for (int i = 0; i < 10; i++) {
                    String itemStr = getName() + "_" + i;
                    dataList.add(itemStr);
                    System.out.println("Added Item:" + itemStr);
                }
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
