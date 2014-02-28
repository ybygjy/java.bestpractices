package org.ybygjy.pattern.state;

import org.ybygjy.pattern.state.impl.HasQuarterState;
import org.ybygjy.pattern.state.impl.NoQuarterState;
import org.ybygjy.pattern.state.impl.SoldOutState;
import org.ybygjy.pattern.state.impl.SoldState;
import org.ybygjy.pattern.state.impl.WinnerState;

/**
 * 糖果机
 * @author WangYanCheng
 * @version 2010-11-13
 */
public class GumballMachine {
    /** 销售状态 */
    private State soldState;
    /** 售罄状态 */
    private State soldOutState;
    /** 投币状态 */
    private State hasQuarterState;
    /** 未投币状态 */
    private State noQuarterState;
    /** */
    private State winnerState;
    /** 当前状态 */
    private State currState;
    /** 糖果数量 */
    private volatile int count;

    /**
     * Constructor
     * @param count 初始糖果数量
     */
    public GumballMachine(int count) {
        this.count = count;
        this.soldState = new SoldState(this);
        this.soldOutState = new SoldOutState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.noQuarterState = new NoQuarterState(this);
        this.winnerState = new WinnerState(this);
        if (count > 0) {
            this.currState = noQuarterState;
        } else {
            this.currState = soldOutState;
        }
    }
    /**
     * 抽币
     */
    public void insertQuarter() {
        currState.insertQuarter();
    }
    /**
     * 退币
     */
    public void ejectQuarter() {
        currState.enjectQuarter();
    }
    /**
     * 转动手柄
     */
    public void turnCrank() {
        currState.turnCrank();
        currState.dispense();
    }
    /**
     * @return the soldState
     */
    public State getSoldState() {
        return soldState;
    }

    /**
     * @return the soldOutState
     */
    public State getSoldOutState() {
        return soldOutState;
    }

    /**
     * @return the hasQuarterState
     */
    public State getHasQuarterState() {
        return hasQuarterState;
    }

    /**
     * @return the noQuarterState
     */
    public State getNoQuarterState() {
        return noQuarterState;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param currState the currState to set
     */
    public void setCurrState(State currState) {
        this.currState = currState;
    }

    /**
     * do release a Gumball
     */
    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (count != 0) {
            --this.count;
        }
    }
    /**
     * @return the winnerState
     */
    public State getWinnerState() {
        return this.winnerState;
    }
    @Override
    public String toString() {
        return "Gumball Count:" + this.count + "\n"
        + "Machine is waiting for quarter";
    }
}
