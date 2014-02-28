package org.ybygjy.pattern.visitor.receiver;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

/**
 * Central Processing Unit
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class Cpu implements Equipment {

    public void accept(Visitor visitor) {
        visitor.visitCPU(this);
    }

    public double price() {
        return 300D;
    }

}
