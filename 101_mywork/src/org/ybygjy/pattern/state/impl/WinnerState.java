package org.ybygjy.pattern.state.impl;

import org.ybygjy.pattern.state.GumballMachine;
import org.ybygjy.pattern.state.State;

/**
 * 嬴家状态
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class WinnerState implements State {
    /** 持有引用 */
    private GumballMachine gumballMachine;

    /**
     * Constructor
     * @param gumballMachine {@link GumballMachine}
     */
    public WinnerState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("You're a winner! You get two gumballs for your quarter.");
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() == 0) {
            gumballMachine.setCurrState(gumballMachine.getSoldOutState());
        } else {
            gumballMachine.releaseBall();
            if (gumballMachine.getCount() > 0) {
                gumballMachine.setCurrState(gumballMachine.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumballs.");
                gumballMachine.setCurrState(gumballMachine.getSoldOutState());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void enjectQuarter() {
        System.out.println("糖果已经发放，不能退币.");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("请稍等。");
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("不需要重复转动手柄.");
    }

}
