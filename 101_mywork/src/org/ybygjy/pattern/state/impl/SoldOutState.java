package org.ybygjy.pattern.state.impl;

import org.ybygjy.pattern.state.GumballMachine;
import org.ybygjy.pattern.state.State;

/**
 * 售罄
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class SoldOutState implements State {
    /**Context instance*/
    private GumballMachine gumballMachine;
    /**
     * Constructor
     * @param gumballMachine {@link GumballMachine}
     */
    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("对不起，糖果已经售罄");
    }

    /**
     * {@inheritDoc}
     */
    public void enjectQuarter() {
        System.out.println("您还没有投入钱币.");
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("对不起，糖果已经售罄");
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("对不起，糖果已经售罄");
    }
}
