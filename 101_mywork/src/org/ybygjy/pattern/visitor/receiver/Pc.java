package org.ybygjy.pattern.visitor.receiver;

/**
 * Personal Computer
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class Pc extends Composite {
    public Pc() {
        super.add(new IntegratedBoard());
        super.add(new HardDisk());
        super.add(new Case());
    }
}
