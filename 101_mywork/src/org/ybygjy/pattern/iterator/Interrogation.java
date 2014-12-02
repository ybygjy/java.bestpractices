package org.ybygjy.pattern.iterator;

import org.ybygjy.pattern.iterator.purchase.PurchaseOfCopA;
import org.ybygjy.pattern.iterator.purchase.PurchaseOfCopB;

/**
 * 客户端类(审讯)
 * @author WangYanCheng
 * @version 2012-11-26
 */
public class Interrogation {
    private Purchase purchaseA;
    private Purchase purchaseB;
    public void doWork() {
        purchaseA = new PurchaseOfCopA();
        purchaseB = new PurchaseOfCopB();
        doIterate("purchaseA.createIterator()", purchaseA.createIterator());
        doIterate("purchaseB.createIterator()", purchaseB.createIterator());
    }
    private void doIterate(String flag, Iterator iterator) {
        System.out.println("Starts for iterator ".concat(flag));
        while (!iterator.isDone()) {
            System.out.println(iterator.currentItem());
            iterator.next();
        }
        System.out.println("Ends for iterator ".concat(flag));
    }
    public static void main(String[] args) {
        Interrogation interrogation = new Interrogation();
        interrogation.doWork();
    }
}
