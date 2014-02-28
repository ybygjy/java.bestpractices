package org.ybygjy.basic.thinking.thread;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * 父类的synchronized方法可以在子类中改变为非synchronized方法
 * @author WangYanCheng
 * @version 2012-11-12
 */
public class SynExtends {
    public static void main(String[] args) {
        ParentClass pcInst = new SubClass();//new ParentClass();
        Set<Integer> resultSets =Collections.synchronizedSet(new TreeSet<Integer>());
        createThread(pcInst, resultSets);
        createThread(pcInst, resultSets);
        createThread(pcInst, resultSets);
        createThread(pcInst, resultSets);
    }
    public static void createThread(final ParentClass pcInst, final Set<Integer> valueSet) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    Integer tmpI = pcInst.doWork();
                    if (!valueSet.add(tmpI)) {
                        throw new IllegalStateException("数据状态不正确，出现重复：".concat(tmpI.toString()));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
class ParentClass {
    private volatile int index;
    public synchronized Integer doWork() {
        return index++;
    }
}
class SubClass extends ParentClass {
    private int index;
    @Override
    public Integer doWork() {
        return index++;
    }
}