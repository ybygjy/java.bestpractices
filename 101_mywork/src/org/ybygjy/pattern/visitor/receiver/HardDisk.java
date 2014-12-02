package org.ybygjy.pattern.visitor.receiver;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

/**
 * 硬盘
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class HardDisk implements Equipment {

    public void accept(Visitor visitor) {
        visitor.visitHardDisk(this);
    }

    public double price() {
        return 300D;
    }

}
