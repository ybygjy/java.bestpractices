package org.ybygjy.pattern.proxy.rmistate.state;

import java.rmi.RemoteException;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;

/**
 * WinnerState
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class WinnerState implements IState {
    /**
     * serial
     */
    private static final long serialVersionUID = -2788659924721859279L;
    /** gumballInst */
    private transient GumballMachine gumballInst;

    /**
     * Constructor
     * @param gumballInst gumballInst
     */
    public WinnerState(GumballMachine gumballInst) {
        this.gumballInst = gumballInst;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("You are a winner! You get two gumballs for your quarter.");
        gumballInst.releaseBall();
        try {
            if (gumballInst.getCount() == 0) {
                gumballInst.setCurrState(gumballInst.getSoldOutState());
            } else {
                gumballInst.releaseBall();
                if (gumballInst.getCount() > 0) {
                    gumballInst.setCurrState(gumballInst.getNoQuarterState());
                } else {
                    gumballInst.setCurrState(gumballInst.getSoldOutState());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void ejectQuarter() {
        System.out.println("已经发放糖果，不能退币。");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("正在发放糖果，请稍候。。");
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("重复转动手柄也不会获得多个糖果。。");
    }

}
