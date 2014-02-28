package org.ybygjy.pattern.iterator.purchase;

import org.ybygjy.pattern.iterator.Iterator;
import org.ybygjy.pattern.iterator.Purchase;
import org.ybygjy.pattern.iterator.iter.BackwardIterator;

/**
 * ¾¯²ìB
 * @author WangYanCheng
 * @version 2012-11-26
 */
public class PurchaseOfCopB extends Purchase {
    public PurchaseOfCopB() {
        append("PC");
        append("Digital Camera");
        append("Dish Washer");
    }
    @Override
    public Iterator createIterator() {
        return new BackwardIterator(this);
    }

}
