package org.ybygjy.pattern.iterator.iter;

import org.ybygjy.pattern.iterator.Iterator;
import org.ybygjy.pattern.iterator.Purchase;

/**
 * ∫Û–Ú±È¿˙
 * @author WangYanCheng
 * @version 2012-11-26
 */
public class BackwardIterator implements Iterator {
    private int state;
    private Purchase purchase;

    public BackwardIterator(Purchase purchase) {
        this.purchase = purchase;
        this.state = purchase.count() - 1;
    }

    public void first() {
        this.state = purchase.count() - 1;
    }

    public void next() {
        if (!isDone()) {
            this.state --;
        }
    }

    public boolean isDone() {
        return this.state < 0;
    }

    public Object currentItem() {
        return this.purchase.currentItem(state);
    }

}
