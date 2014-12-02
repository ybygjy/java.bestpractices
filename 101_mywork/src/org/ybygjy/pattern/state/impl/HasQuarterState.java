package org.ybygjy.pattern.state.impl;

import java.util.Random;

import org.ybygjy.pattern.state.GumballMachine;
import org.ybygjy.pattern.state.State;

/**
 * 投币
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class HasQuarterState implements State {
    /** 持有引用 */
    private GumballMachine gumballMachine;
    /**random winner*/
    private Random randomWinner;
    /**
     * Constructor
     * @param gumballMachine {@link GumballMachine}
     */
    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
        this.randomWinner = new Random(System.currentTimeMillis());
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("No gumball dispended.");
    }

    /**
     * {@inheritDoc}
     */
    public void enjectQuarter() {
        System.out.println("Quarter returned.");
        gumballMachine.setCurrState(gumballMachine.getNoQuarterState());
    }

    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("You can't insert another quarter.");
    }

    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("You turned..");
        int winner = randomWinner.nextInt(10);
        if (winner == 0 && gumballMachine.getCount() > 1) {
            gumballMachine.setCurrState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setCurrState(gumballMachine.getSoldState());
        }
    }

}
