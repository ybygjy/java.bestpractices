package org.ybygjy.pattern.state.impl;

import org.ybygjy.pattern.state.GumballMachine;
import org.ybygjy.pattern.state.State;

/**
 * 售出
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class SoldState implements State {
    /**持有引用*/
    private GumballMachine gumballMachine;
    /**
     * Constructor
     * @param gumballMachine {@link GumballMachine}
     */
    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
    /**
     * {@inheritDoc}
     */
    public void dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.setCurrState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("Oops, out of gumballs.");
            gumballMachine.setCurrState(gumballMachine.getSoldOutState());
        }
    }
    /**
     * {@inheritDoc}
     */
    public void enjectQuarter() {
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
