package org.ybygjy.pattern.proxy.rmistate.state;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;

/**
 * SoldOutState
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class SoldOutState implements IState {
    /**
     * serialized
     */
    private static final long serialVersionUID = 3836350194815985454L;
    /** gumballInst */
    private transient GumballMachine gumballInst;

    /**
     * Constructor
     * @param gumballInst gumballInst
     */
    public SoldOutState(GumballMachine gumballInst) {
        this.gumballInst = gumballInst;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        this.gumballInst.setCurrState(this.gumballInst.getSoldOutState());
        System.out.println("糖果已经售罄.");
    }

    /**
     * {@inheritDoc}
     */
    public void ejectQuarter() {
        System.out.println("糖果已经售罄.");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("糖果已经售罄.");
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("糖果已经售罄.");
    }

}
