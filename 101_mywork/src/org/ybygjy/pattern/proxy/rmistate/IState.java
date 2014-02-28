package org.ybygjy.pattern.proxy.rmistate;

import java.io.Serializable;

/**
 * Gumball×´Ì¬·â×°
 * @author WangYanCheng
 * @version 2010-12-24
 */
public interface IState extends Serializable {
    /**
     * doInsertQuarter
     */
    public void insertQuarter();

    /**
     * ejectQuarter
     */
    public void ejectQuarter();

    /**
     * turnCrank
     */
    public void turnCrank();

    /**
     * dispense
     */
    public void dispense();
}
