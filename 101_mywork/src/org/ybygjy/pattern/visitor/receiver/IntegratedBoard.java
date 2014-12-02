package org.ybygjy.pattern.visitor.receiver;

/**
 * 集成电路板
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class IntegratedBoard extends Composite {
    public IntegratedBoard() {
        super.add(new MainBoard());
        super.add(new Cpu());
    }
    public double price() {
        return 200D;
    }
}
