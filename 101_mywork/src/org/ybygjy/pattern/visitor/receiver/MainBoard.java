package org.ybygjy.pattern.visitor.receiver;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

/**
 * Ö÷°å
 * @author WangYanCheng
 * @version 2013-2-6
 */
public class MainBoard implements Equipment {

    public void accept(Visitor visitor) {
        visitor.visitMainBoard(this);
    }

    public double price() {
        return 1000D;
    }

}
