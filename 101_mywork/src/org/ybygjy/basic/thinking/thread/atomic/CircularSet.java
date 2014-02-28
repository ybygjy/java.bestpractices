package org.ybygjy.basic.thinking.thread.atomic;

import org.ybygjy.basic.thinking.thread.testframework.Invariant;
import org.ybygjy.basic.thinking.thread.testframework.InvariantWatcher;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantFailure;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantOK;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantState;

/**
 * Reuses storage so we don't run out of memory
 * @author WangYanCheng
 * @version 2010-9-30
 */
public class CircularSet implements Invariant {
    /** array */
    private int[] array;
    /** array's len */
    private int len;
    /** array's current index */
    private int index;

    /**
     * Constructor
     * @param size size
     */
    public CircularSet(int size) {
        array = new int[size];
        len = size;
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    /**
     * addElement
     * @param i i
     */
    public synchronized void add(int i) {
        array[index] = i;
        index = ++index % len;
    }

    /**
     * is contains
     * @param val val
     * @return true/false
     */
    public synchronized boolean contains(int val) {
        for (int i = 0; i < len; i++) {
            if (array[i] == val) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public InvariantState invariant() {
        int serial = SerialNumberGenerator.nextSerialNumber();
        if (this.contains(serial)) {
            return new InvariantFailure(serial);
        }
        this.add(serial);
        return new InvariantOK();
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws InterruptedException InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CircularSet circularSet = new CircularSet(1000);
        new InvariantWatcher(circularSet, 10000);
        new InvariantWatcher(circularSet, 10000);
        new InvariantWatcher(circularSet, 10000);
        Thread.currentThread().join();
    }
}
