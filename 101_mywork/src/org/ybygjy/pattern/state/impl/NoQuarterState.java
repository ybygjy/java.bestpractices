package org.ybygjy.pattern.state.impl;

import org.ybygjy.pattern.state.GumballMachine;
import org.ybygjy.pattern.state.State;

/**
 * 未投币
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class NoQuarterState implements State {
    /** 持有引用 */
    private GumballMachine gumballMachine;

    /**
     * Constructor
     * @param gumballMachine {@link GumballMachine}
     */
    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
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
    public void enjectQuarter() {
        System.out.println("You haven't inserted a quarter.");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("You insert a quarter.");
        gumballMachine.setCurrState(gumballMachine.getHasQuarterState());
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("You turned, but there's no quarter.");
    }
}
