package org.ybygjy.pattern.visitor.receiver;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;
/**
 * »úÏä
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class Case implements Equipment {

    public void accept(Visitor visitor) {
        visitor.visitCase(this);
    }

    public double price() {
        return 15.0D;
    }

}
