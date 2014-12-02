package org.ybygjy.pattern.proxy.rmistate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.ybygjy.pattern.proxy.rmistate.state.HasQuarterState;
import org.ybygjy.pattern.proxy.rmistate.state.NoQuarterState;

/**
 * 糖果机实例
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class GumballMachine extends UnicastRemoteObject implements IGumballMachine {
    /**
     * serialized
     */
    private static final long serialVersionUID = -974874152922137911L;
    /** gumball count */
    private volatile int count;
    /** location */
    private String location;
    /** currState */
    private IState currState;
    /** hasQuarterState */
    private IState hasQuarterState;
    /** noQuarterState */
    private IState noQuarterState;
    /** soldOutState */
    private IState soldOutState;
    /** soldState */
    private IState soldState;
    /** winnerState */
    private IState winnerState;

    /**
     * Constructor
     * @param location location
     * @param count count
     * @throws RemoteException RemoteException
     */
    public GumballMachine(String location, int count) throws RemoteException {
        this.location = location;
        this.count = count;
        this.hasQuarterState = new HasQuarterState(this);
        this.noQuarterState = new NoQuarterState(this);
        if (count > 0) {
            this.currState = noQuarterState;
        } else {
            this.currState = soldOutState;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getCount() throws RemoteException {
        return this.count;
    }

    /**
     * getLocation
     * @return location
     * @throws RemoteException RemoteException
     */
    public String getLocation() throws RemoteException {
        return this.location;
    }

    /**
     * getState
     * @return currState
     * @throws RemoteException RemoteException
     */
    public IState getState() throws RemoteException {
        return this.currState;
    }

    /**
     * @return the currState
     */
    public IState getCurrState() {
        return currState;
    }

    /**
     * @param currState the currState to set
     */
    public void setCurrState(IState currState) {
        this.currState = currState;
    }

    /**
     * @return the hasQuarterState
     */
    public IState getHasQuarterState() {
        return hasQuarterState;
    }

    /**
     * @return the noQuarterState
     */
    public IState getNoQuarterState() {
        return noQuarterState;
    }

    /**
     * @return the soldOutState
     */
    public IState getSoldOutState() {
        return soldOutState;
    }

    /**
     * @return the winnerState
     */
    public IState getWinnerState() {
        return winnerState;
    }

    /**
     * @return the soldState
     */
    public IState getSoldState() {
        return soldState;
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
}
