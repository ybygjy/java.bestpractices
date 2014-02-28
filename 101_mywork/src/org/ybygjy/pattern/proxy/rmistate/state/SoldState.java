package org.ybygjy.pattern.proxy.rmistate.state;

import java.rmi.RemoteException;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;

/**
 * SoldState
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class SoldState implements IState {
    /**
     * serialized
     */
    private static final long serialVersionUID = 1616244174101323467L;
    /**gumballInst*/
    private transient GumballMachine gumballInst;
    /**
     * Constructor
     * @param gumballInst gumballInst
     */
    public SoldState(GumballMachine gumballInst) {
        this.gumballInst = gumballInst;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        gumballInst.releaseBall();
        try {
            if (gumballInst.getCount() > 0) {
                gumballInst.setCurrState(gumballInst.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumballs.");
                gumballInst.setCurrState(gumballInst.getSoldOutState());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    /**
     * {@inheritDoc}
     */
    public void ejectQuarter() {
        System.out.println("Sorry, you already turned the crank.");
    }
    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("Please wait, we're already giving you a gumball.");
    }
    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("Turning twice doesn't get you another gumball.");
    }
}
