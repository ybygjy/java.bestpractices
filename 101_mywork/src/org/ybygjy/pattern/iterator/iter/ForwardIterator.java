package org.ybygjy.pattern.iterator.iter;

import org.ybygjy.pattern.iterator.Iterator;
import org.ybygjy.pattern.iterator.Purchase;

/**
 * Ç°Ğò±éÀú
 * @author WangYanCheng
 * @version 2012-11-26
 */
public class ForwardIterator implements Iterator {
    private int state;
    private Purchase purchase;

    public ForwardIterator(Purchase purchase) {
        this.purchase = purchase;
    }

    public void first() {
        this.state = 0;
    }

    public void next() {
        if (!isDone()) {
            this.state ++;
        }
    }

    public boolean isDone() {
        if (state > purchase.count() - 1) {
            return true;
        }
        return false;
    }

    public Object currentItem() {
        return purchase.currentItem(state);
    }

}
