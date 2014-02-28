package org.ybygjy.pattern.proxy.rmistate.state;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;

/**
 * NoQuarterState
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class NoQuarterState implements IState {
    /**
     * serialized
     */
    private static final long serialVersionUID = -6995548886185343221L;
    /** gumballInst */
    private transient GumballMachine gumballInst;

    /**
     * Constructor
     * @param gumballInst {@link GumballMachine}
     */
    public NoQuarterState(GumballMachine gumballInst) {
        this.gumballInst = gumballInst;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("You need to pay first.");
    }

    /**
     * {@inheritDoc}
     */
    public void ejectQuarter() {
        System.out.println("You haven't inserted a quarter.");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("You insert a quarter.");
        gumballInst.setCurrState(gumballInst.getHasQuarterState());
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("You turned, but there's no quarter.");
    }
}
